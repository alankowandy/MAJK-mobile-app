package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.majk.app.Route
import org.example.majk.majk.data.dto.MedicineEntryDto
import org.example.majk.majk.data.dto.ReleaseScheduleDto
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.domain.ReleaseSchedule


class ScheduledMedicineListViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val accountId = savedStateHandle.toRoute<Route.MajkScheduleMedicineList>().accountId

    private val _state = MutableStateFlow(ScheduledMedicineListState())
    val state = _state.asStateFlow()

    private val _medicineList = MutableStateFlow<List<MedicineEntry>>(emptyList())

    init {
        fetchScheduledMedicine(accountId)
    }

    fun onAction(action: ScheduledMedicineListAction) {
        when (action) {
            is ScheduledMedicineListAction.OnBackClick -> {

            }
            is ScheduledMedicineListAction.OnNoteDetailsClick -> {

            }
            is ScheduledMedicineListAction.OnDeleteClick -> {
                deleteScheduledMedicine(action.scheduleId)
            }
            is ScheduledMedicineListAction.OnToggleNoteEditor -> {
                _medicineList.update { list ->
                    list.map { entry ->
                        if (entry.releaseId == action.releaseId) {
                            entry.copy(isTextEditorVisible = !entry.isTextEditorVisible)
                        } else {
                            entry
                        }
                    }
                }
                _state.update { it.copy(medicineList = _medicineList.value) }
            }
            is ScheduledMedicineListAction.OnNoteChange -> {
                _medicineList.update { list ->
                    list.map { entry ->
                        if (entry.releaseId == action.releaseId) {
                            entry.copy(note = action.note)
                        } else {
                            entry
                        }
                    }
                }
                _state.update { it.copy(medicineList = _medicineList.value) }
            }
            is ScheduledMedicineListAction.OnSaveNoteClick -> {
                saveNote(
                    releaseId = action.releaseId,
                    note = action.note
                )
            }
            is ScheduledMedicineListAction.OnDismissDialog -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun fetchScheduledMedicine(accountId: Long) {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchMedicamentEntries(accountId)
                    .map { it.asDomainModel() }
                    .sortedBy { it.releaseId }
                _medicineList.emit(result)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        medicineList = _medicineList.value
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Błąd"
                    )
                }
                println(error)
            }
        }
    }

    private fun deleteScheduledMedicine(releaseId: Long) {
        viewModelScope.launch {
            runCatching {
                appRepository.deleteScheduledMedicine(releaseId)
            }.onSuccess {
                fetchScheduledMedicine(accountId)
            }.onFailure { error ->
                _state.update { it.copy(errorMessage = "Błąd przy usuwaniu.") }
                println(error)
            }
        }
    }

    private fun saveNote(releaseId: Long, note: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    saveStatuses = it.saveStatuses + (releaseId to SaveStatus.Saving)
                )
            }
            runCatching {
                appRepository.updateNote(releaseId, note)
            }.onSuccess {
                _state.update {
                    it.copy(
                        saveStatuses = it.saveStatuses + (releaseId to SaveStatus.Success)
                    )
                }
                delay(1500)
                _state.update {
                    it.copy(
                        saveStatuses = it.saveStatuses + (releaseId to SaveStatus.Idle)
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        saveStatuses = it.saveStatuses + (releaseId to SaveStatus.Idle)
                    )
                }
            }
        }
    }

    private fun MedicineEntryDto.asDomainModel(): MedicineEntry {
        return MedicineEntry(
            releaseId = this.releaseId,
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName,
            startDate = this.startDate,
            endDate = this.endDate,
            repeatingInterval = this.repeatingInterval,
            pillAmount = this.pillAmount,
            mealDependability = this.mealDependability,
            note = this.note,
            leafletUrl = this.leafletUrl,
            accountIdForEdit = accountId
        )
    }
}