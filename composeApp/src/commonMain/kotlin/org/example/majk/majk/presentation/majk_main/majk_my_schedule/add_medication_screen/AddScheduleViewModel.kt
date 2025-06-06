package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.FlowPreview
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.app.Route
import org.example.majk.majk.data.dto.ContainerSettingsSearchQueryDto
import org.example.majk.majk.data.dto.FetchFamilyIdDto
import org.example.majk.majk.domain.ContainerSettingsSearchQuery
import org.example.majk.majk.domain.FetchFamilyId


class AddScheduleViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val accountId = savedStateHandle.toRoute<Route.MajkAddSchedule>().accountId

    private val _state = MutableStateFlow(AddScheduleState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResult = MutableStateFlow<List<ContainerSettingsSearchQuery>>(emptyList())

    private val _familyId = MutableStateFlow<FetchFamilyId?>(null)

    init {
        fetchFamilyId(accountId)
        observeSearchText()
    }

    fun onAction(action: AddScheduleAction) {
        when (action) {
            is AddScheduleAction.OnBackClick -> {}
            is AddScheduleAction.OnSelectedMedicineChange -> {
                _state.update { it.copy(
                  medicine = action.medicine
                ) }
                if (_state.value.medicine != null) {
                    val startDate = LocalDateTime.parse(_state.value.medicine!!.startDate).date
                    val endDate = LocalDateTime.parse(_state.value.medicine!!.endDate).date
                    val time = LocalDateTime.parse(_state.value.medicine!!.startDate).time
                    val beforeMeal = _state.value.medicine!!.mealDependability == "before"
                    val duringMeal = _state.value.medicine!!.mealDependability == "during"
                    val afterMeal = _state.value.medicine!!.mealDependability == "after"
                    _searchQuery.value = _state.value.medicine!!.medicamentName
                    _state.update {
                        it.copy(
                            selectedMedicamentId = _state.value.medicine!!.medicamentId,
                            startDateValue = startDate,
                            endDateValue = endDate,
                            time = time,
                            intervalDays = _state.value.medicine!!.repeatingInterval.toInt(),
                            pillAmount = _state.value.medicine!!.pillAmount.toInt(),
                            beforeMeal = beforeMeal,
                            duringMeal = duringMeal,
                            afterMeal = afterMeal,
                            note = _state.value.medicine!!.note ?: ""
                        )
                    }
                }
            }
            is AddScheduleAction.OnSearchQueryChange -> {
                _searchQuery.value = action.medicamentSearch
                _state.update { it.copy(selectedMedicamentId = action.medicamentId) }
            }
            is AddScheduleAction.OnSearchExpandedChange -> {
                _state.update { it.copy(isSearchExpanded = action.isExpanded) }
            }
            is AddScheduleAction.OnStartDateClick -> {
                _state.update { it.copy(isStartDateVisible = !it.isStartDateVisible) }
            }
            is AddScheduleAction.OnEndDateClick -> {
                _state.update { it.copy(isEndDateVisible = !it.isEndDateVisible) }
            }
            is AddScheduleAction.OnStartDatePick -> {
                val date = longToLocalDate(action.date)
                _state.update {
                    it.copy(startDateValue = date)
                }
            }
            is AddScheduleAction.OnEndDatePick -> {
                val date = longToLocalDate(action.date)
                _state.update {
                    it.copy(endDateValue = date)
                }
            }
            is AddScheduleAction.OnTimePickClick -> {
                _state.update { it.copy(isTimePickerVisible = !it.isTimePickerVisible) }
            }
            is AddScheduleAction.OnTakingTimePick -> {
                _state.update { it.copy(time = action.time) }
            }
            is AddScheduleAction.OnDayIntervalClick -> {
                _state.update { it.copy(isIntervalDropdownVisible = !it.isIntervalDropdownVisible) }
            }
            is AddScheduleAction.OnTakingIntervalChange -> {
                _state.update { it.copy(intervalDays = action.days) }
            }
            is AddScheduleAction.OnPillAmountClick -> {
                _state.update { it.copy(isPillAmountDropdownVisible = !it.isPillAmountDropdownVisible) }
            }
            is AddScheduleAction.OnPillAmountChange -> {
                _state.update { it.copy(pillAmount = action.pillQuantity) }
            }
            is AddScheduleAction.OnToggleBeforeMeal -> {
                if (_state.value.duringMeal) {
                    _state.update { it.copy(
                        beforeMeal = !it.beforeMeal,
                        duringMeal = !it.duringMeal
                    ) }
                } else if (_state.value.afterMeal) {
                    _state.update { it.copy(
                        beforeMeal = !it.beforeMeal,
                        afterMeal = !it.afterMeal
                    ) }
                } else {
                    _state.update { it.copy(beforeMeal = !it.beforeMeal) }
                }
            }
            is AddScheduleAction.OnToggleDuringMeal -> {
                if (_state.value.beforeMeal) {
                    _state.update { it.copy(
                        beforeMeal = !it.beforeMeal,
                        duringMeal = !it.duringMeal
                    ) }
                } else if (_state.value.afterMeal) {
                    _state.update { it.copy(
                        duringMeal = !it.duringMeal,
                        afterMeal = !it.afterMeal
                    ) }
                } else {
                    _state.update { it.copy(duringMeal = !it.duringMeal) }
                }
            }
            is AddScheduleAction.OnToggleAfterMeal -> {
                if (_state.value.beforeMeal) {
                    _state.update { it.copy(
                        beforeMeal = !it.beforeMeal,
                        afterMeal = !it.afterMeal
                    ) }
                } else if (_state.value.duringMeal) {
                    _state.update { it.copy(
                        duringMeal = !it.duringMeal,
                        afterMeal = !it.afterMeal
                    ) }
                } else {
                    _state.update { it.copy(afterMeal = !it.afterMeal) }
                }
            }
            is AddScheduleAction.OnNoteChange -> {
                _state.update { it.copy(note = action.text) }
            }
            is AddScheduleAction.OnSaveClick -> {
                if (_state.value.selectedMedicamentId != 0L &&
                    _state.value.startDateValue != null &&
                    _state.value.endDateValue != null &&
                    _state.value.time != null
                ) {
                    val startDate = formatLocalDateTime(_state.value.startDateValue!!, _state.value.time!!)
                    val endDate = formatLocalDateTime(_state.value.endDateValue!!, _state.value.time!!)
                    val consumption = if (_state.value.beforeMeal) "before"
                        else if (_state.value.duringMeal) "during"
                        else "after"
                    if (_state.value.medicine == null) {
                        insertNewSchedule(
                            medicamentId = _state.value.selectedMedicamentId,
                            accountId = accountId,
                            startDate = startDate,
                            endDate = endDate,
                            dayInterval = _state.value.intervalDays.toLong(),
                            pillAmount = _state.value.pillAmount.toLong(),
                            consumption = consumption,
                            note = _state.value.note
                        )
                    } else {
                        updateSchedule(
                            releaseId = _state.value.medicine!!.releaseId,
                            medicamentId = _state.value.selectedMedicamentId,
                            startDate = startDate,
                            endDate = endDate,
                            dayInterval = _state.value.intervalDays.toLong(),
                            pillAmount = _state.value.pillAmount.toLong(),
                            consumption = consumption,
                            note = _state.value.note
                        )
                    }
                } else {
                    _state.update { it.copy(errorMessage = "Nie wszystkie dane zostały poprawnie podane!") }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchText() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .onEach { _state.update { it.copy(isSearching = true) } }
                .collect { text ->
                    if (text.isBlank()) {
                        _state.value.familyIdByAccountId?.let { fetchMyMedicament(it) }
                    } else {
                        _state.value.familyIdByAccountId?.let { searchMedicament(it, text) }
                    }
                }
        }
    }

    private fun fetchFamilyId(accountId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchFamilyId(accountId)
                _familyId.emit(result.asDomainModel())
            }.onSuccess {
                _state.update { it.copy(familyIdByAccountId = _familyId.value?.familyId) }
            }.onFailure { error ->
                println(error)
            }
        }
    }

    private fun fetchMyMedicament(familyId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchMyMedicament(familyId)
                _searchResult.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update {
                    it.copy(
                        isSearching = false,
                        searchResult = _searchResult.value
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isSearching = false,
                        errorMessage = "Błąd"
                    )
                }
            }
        }
    }

    private fun searchMedicament(familyId: Long, partialName: String) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.searchMedicament(familyId, partialName)
                _searchResult.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update {
                    it.copy(
                        isSearching = false,
                        searchResult = _searchResult.value
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(isSearching = false)
                }
            }
        }
    }

    private fun insertNewSchedule(
        medicamentId: Long,
        accountId: Long,
        startDate: String,
        endDate: String,
        dayInterval: Long,
        pillAmount: Long,
        consumption: String,
        note: String
    ) {
        viewModelScope.launch {
            runCatching {
                appRepository.insertNewSchedule(medicamentId, accountId, startDate, endDate, dayInterval, pillAmount, consumption, note)
            }.onSuccess {

            }.onFailure { error ->
                println(error)
            }
        }
    }

    private fun updateSchedule(
        releaseId: Long,
        medicamentId: Long,
        startDate: String,
        endDate: String,
        dayInterval: Long,
        pillAmount: Long,
        consumption: String,
        note: String
    ) {
        viewModelScope.launch {
            runCatching {
                appRepository.updateSchedule(releaseId, medicamentId, startDate, endDate, dayInterval, pillAmount, consumption, note)
            }.onSuccess {

            }.onFailure { error ->
                println(error)
            }
        }
    }

    private fun formatLocalDateTime(
        date: LocalDate,
        time: LocalTime
    ): String {
       return LocalDateTime(date, time).toString()
    }

    private fun longToLocalDate(date: Long?): LocalDate? {
        if (date != null) {
            val instant = Instant.fromEpochMilliseconds(date)
            return instant
                .toLocalDateTime(TimeZone.UTC)
                .date
        } else {
            return null
        }
    }

    private fun ContainerSettingsSearchQueryDto.asDomainModel(): ContainerSettingsSearchQuery {
        return ContainerSettingsSearchQuery(
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName
        )
    }

    private fun FetchFamilyIdDto.asDomainModel(): FetchFamilyId {
        return FetchFamilyId(
            familyId = this.familyId
        )
    }
}