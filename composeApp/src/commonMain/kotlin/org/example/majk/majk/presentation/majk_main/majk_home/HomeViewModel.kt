package org.example.majk.majk.presentation.majk_main.majk_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.daysUntil
import org.example.majk.majk.data.dto.ContainerStateDto
import org.example.majk.majk.data.dto.ReleaseScheduleDto
import org.example.majk.majk.domain.ContainerState
import org.example.majk.majk.domain.ReleaseSchedule


class HomeViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _containerList = MutableStateFlow<List<ContainerState>>(emptyList())
    private val _releaseSchedule = MutableStateFlow<List<ReleaseSchedule>>(emptyList())

    init {
        fetchUserInfo()
        observeWhenFinishedLoading()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnDismissDialog -> {
                _state.update { it.copy(errorMessage = null) }
            }
            is HomeAction.OnNextHourCalc -> {

            }
        }
    }

    private fun fetchUserInfo() {
        sharedViewModel.userInfo
            .filterNotNull()
            .distinctUntilChanged()
            .onEach { userInfo ->
                userInfo.accountId?.let { fetchReleaseSchedule(it) }
                userInfo.deviceId?.let { fetchContainers(it) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeWhenFinishedLoading() {
        viewModelScope.launch {
            combine(
                _releaseSchedule,
                _containerList
            ) { releaseSchedule, containerList ->
                Pair(releaseSchedule, containerList)
            }
                .filter { (releaseSchedule, containerList) ->
                true
            }
                .first()
                .let {
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun fetchReleaseSchedule(accountId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchReleaseSchedule(accountId)
                _releaseSchedule.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update { it.copy(
                    releaseSchedule = _releaseSchedule.value,
                    nextDoseHourForToday = nextDoseHourForToday(
                        schedules = _releaseSchedule.value,
                        currentDay = it.currentDay
                    )
                ) }
            }.onFailure { error ->
                _state.update { it.copy(errorMessage = "Błąd") }
                println(error)
            }
        }
    }

    private fun fetchContainers(deviceId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchContainerState(deviceId)
                    .map { it.asDomainModel() }
                    .sortedBy { it.containerNumber }
                _containerList.emit(result)
            }.onSuccess {
                _state.update { it.copy(
                    containerList = _containerList.value
                ) }
            }.onFailure { error ->
                _state.update { it.copy(errorMessage = "Błąd") }
                println(error)
            }
        }
    }

    private fun nextDoseHourForToday(
        schedules: List<ReleaseSchedule>,
        currentDay: LocalDateTime
    ): Int? {
        // 1) Filter only schedules that “apply today”:
        val today = currentDay.date
        val candidateHours = schedules.mapNotNull { schedule ->
            // parse this schedule’s start and end as LocalDateTime
            val startDT = LocalDateTime.parse(schedule.startDate)
            val endDT   = LocalDateTime.parse(schedule.endDate)

            // if today is before startDate or after endDate → skip
            if (today < startDT.date || today > endDT.date) return@mapNotNull null

            // check repeating interval
            val daysBetween = startDT.date.daysUntil(today)
            if (daysBetween % schedule.repeatingInterval != 0L) return@mapNotNull null

            // this schedule “fires” today, so return its hour‐of‐day
            startDT.time.hour
        }
        // 2) Out of all those hours, keep only the ones ≥ currentHour
        val futureHours = candidateHours.filter { hourOfDay ->
            hourOfDay >= currentDay.time.hour
        }
        // 3) Return the minimum, or null if empty
        return futureHours.minOrNull()
    }

    private fun ReleaseScheduleDto.asDomainModel(): ReleaseSchedule {
        return ReleaseSchedule(
            releaseId = this.releaseId,
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName,
            startDate = this.startDate,
            endDate = this.endDate,
            repeatingInterval = this.repeatingInterval,
            pillAmount = this.pillAmount,
            mealDependability = this.mealDependability,
            releaseHistoryId = this.releaseHistoryId,
            releaseDateTime = this.releaseDateTime
        )
    }

    private fun ContainerStateDto.asDomainModel(): ContainerState {
        return ContainerState(
            containerId = this.containerId,
            containerState = this.containerState,
            pillQuantity = this.pillQuantity,
            containerNumber = this.containerNumber,
            medicamentName = this.medicamentName
        )
    }
}