package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.app.Route
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.dto.ReleaseScheduleDto
import org.example.majk.majk.domain.ReleaseSchedule


class DetailsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    val date =
        LocalDate.parse(savedStateHandle.toRoute<Route.MajkScheduleDetailsByDate>().date)

    private val _state = MutableStateFlow(DetailsState(selectedDate = date))
    val state = _state.asStateFlow()

    private val _releaseSchedule = MutableStateFlow<List<ReleaseSchedule>>(emptyList())
    val releaseSchedule = _releaseSchedule.asStateFlow()

    private var currentAccountId: Long = 0L

    init {
        fetchAccountId()
    }

    fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.OnSelectDate -> {
                val newDate = action.date
                _state.update{
                    it.copy(
                        selectedDate = newDate,
                        schedule = emptyMap()
                    )
                }
            }
            is DetailsAction.OnRefreshCurrentTime -> {
                _state.update {
                    it.copy(
                        currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")),
                        currentHour = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")).hour,
                        currentMinute = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")).minute
                    )
                }
            }
            is DetailsAction.OnScheduledMedicineClick -> {

            }
            is DetailsAction.OnBackClick -> {}
        }
    }

    private fun fetchAccountId() {
        sharedViewModel.userInfo
            .filterNotNull()
            .map { it.accountId }
            .filter { it != 0L }
            .distinctUntilChanged()
            .onEach { accountId ->
                if (accountId != null) {
                    currentAccountId = 1L
                    _state.update { it.copy(currentAccountId = accountId) }
                    fetchScheduledMedicine(_state.value.currentAccountId)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchScheduledMedicine(accountId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchReleaseSchedule(accountId)
                _releaseSchedule.emit(result.map { it.asDomainModel() })
            }.onSuccess {

            }.onFailure { error ->
                println(error)
            }
        }
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
            mealDependability = this.mealDependability
        )
    }
}