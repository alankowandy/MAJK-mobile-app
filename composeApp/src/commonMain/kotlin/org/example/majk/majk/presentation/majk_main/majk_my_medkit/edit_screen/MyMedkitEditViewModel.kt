package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.dto.MedicamentSearchDto
import org.example.majk.majk.domain.MedicamentSearch


class MyMedkitEditViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(MyMedkitEditState())
    val state = _state.asStateFlow()

    private val _searchResult = MutableStateFlow<List<MedicamentSearch>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        fetchAccountDetails()
        observeSearchText()
    }

    fun onAction(action: MyMedkitEditAction) {
        when (action) {
            is MyMedkitEditAction.OnSearchQueryChange -> {
                _searchQuery.value = action.medicamentSearch
            }
            is MyMedkitEditAction.OnSearchExpandedChange -> {
                _state.update {
                    it.copy(isSearchExpanded = action.isExpanded)
                }
            }
            is MyMedkitEditAction.OnStartDatePick -> {
                TODO()
            }
            is MyMedkitEditAction.OnEndDatePick -> {
                TODO()
            }
            is MyMedkitEditAction.OnTakingTimePick -> {
                TODO()
            }
            is MyMedkitEditAction.OnTakingIntervalChange -> {
                _state.update {
                    it.copy(takingInterval = action.days)
                }
            }
            is MyMedkitEditAction.OnPillAmountChange -> {
                _state.update {
                    it.copy(pillTakingAmount = action.pillQuantity)
                }
            }
            is MyMedkitEditAction.OnToggleBeforeMeal -> {
                _state.update {
                    it.copy(beforeMeal = action.value)
                }
            }
            is MyMedkitEditAction.OnToggleDuringMeal -> {
                _state.update {
                    it.copy(duringMeal = action.value)
                }
            }
            is MyMedkitEditAction.OnToggleAfterMeal -> {
                _state.update {
                    it.copy(afterMeal = action.value)
                }
            }
            is MyMedkitEditAction.OnNoteChange -> {
                _state.update {
                    it.copy(note = action.text)
                }
            }
            is MyMedkitEditAction.OnSaveClick -> {
                _state.update {
                    it.copy(
                        selectedMedicamentId = action.medicamentId
                    )
                }
                insertMedicament(
                    medicamentId = _state.value.selectedMedicamentId,
                    familyId = _state.value.currentAccountId
                )
            }
            is MyMedkitEditAction.OnBackClick -> { }
        }
    }

    private fun fetchAccountDetails() {
        viewModelScope.launch {
            sharedViewModel.userInfo
                .map { it?.familyId }
                .filter { it != 0L }
                .distinctUntilChanged()
                .onEach { familyId ->
                    if (familyId != null) {
                        _state.update {
                            it.copy(currentAccountId = familyId)
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchText() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .onEach { _state.update { it.copy(isSearching = true) } }
                .collect { text ->
                    if (text.isBlank()) {
                        fetchInitialMedicamentSet()
                    } else {
                        searchMedicamentSet(text)
                    }
                }
        }
    }

    private fun fetchInitialMedicamentSet() {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchInitialMedicamentSet()
                _searchResult.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update { it.copy(isSearching = false) }
            }.onFailure { error ->
                _state.update { it.copy(
                        isSearching = false,
                        errorMessage = "Błąd"
                    )
                }
                println(error)
            }
        }
    }

    private fun searchMedicamentSet(name: String) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.searchMedicamentSet(name)
                _searchResult.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                _state.update { it.copy(isSearching = false) }
            }.onFailure { error ->
                _state.update { it.copy(
                        isSearching = false,
                        errorMessage = "Błąd"
                    )
                }
                println(error)
            }
        }
    }

    private fun insertMedicament(medicamentId: Long, familyId: Long) {
        viewModelScope.launch {
            runCatching {
                appRepository.insertMedicament(medicamentId, familyId)
            }.onFailure {
                _state.update { it.copy(errorMessage = "Błąd") }
            }
        }
    }

    private fun MedicamentSearchDto.asDomainModel(): MedicamentSearch {
        return MedicamentSearch(
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName,
            medicamentType = this.medicamentType,
            medicamentLeaflet = this.medicamentLeaflet
        )
    }
}