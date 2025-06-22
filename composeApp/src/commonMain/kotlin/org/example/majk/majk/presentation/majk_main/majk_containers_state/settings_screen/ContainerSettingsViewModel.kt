package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.FlowPreview
import org.example.majk.app.Route
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
import org.example.majk.majk.data.dto.ContainerSettingsDto
import org.example.majk.majk.data.dto.ContainerSettingsSearchQueryDto
import org.example.majk.majk.domain.ContainerSettings
import org.example.majk.majk.domain.ContainerSettingsSearchQuery

class ContainerSettingsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val container = savedStateHandle.toRoute<Route.MajkContainerSettings>().containerId

    private val _state = MutableStateFlow(ContainerSettingsState())
    val state = _state.asStateFlow()

    private val _containerSettings = MutableStateFlow(ContainerSettings())

    private val _searchResult = MutableStateFlow<List<ContainerSettingsSearchQuery>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var currentFamilyId: Long = 0

    init {
        fetchFamilyId()
        fetchContainerSettings(containerId = container)
        observeSearchText()
    }

    fun onAction(action: ContainerSettingsAction) {
        when (action) {
            is ContainerSettingsAction.OnSearchQueryChange -> {
                _searchQuery.value = action.medicamentSearch
                _state.update {
                    it.copy(selectedMedicamentId = action.medicamentId)
                }
            }
            is ContainerSettingsAction.OnEmptySearchQueryClick -> {
                emptyContainer(container)
            }
            is ContainerSettingsAction.OnPillQuantityChange -> {
                _state.update { it.copy(pillQuantityEntry = action.pillQuantity) }
            }
            is ContainerSettingsAction.OnSearchExpandedChange -> {
                _state.update { it.copy(isSearchExpanded = action.isExpanded) }
            }
            is ContainerSettingsAction.OnEmptyPillEntryClick -> {
                _state.update { it.copy(pillQuantityEntry = "0") }
            }
            is ContainerSettingsAction.OnConfirmClick -> {
                if (_searchQuery.value != _state.value.initialSearchEntry
                    && _state.value.selectedMedicamentId != -1L) {
                    updateContainerMedicament(
                        containerId = container,
                        medicamentId = _state.value.selectedMedicamentId
                    )
                } else {
                    _state.update { it.copy(searchError = "Wybrany lek jest niepoprawny.") }
                }

                if (_state.value.pillQuantityEntry.isBlank()) {
                    _state.update { it.copy(pillQuantityEntry = "0") }
                }

                if (_state.value.pillQuantityEntry.toInt() > 0) {
                    val diff = _state.value.pillQuantityEntry.toLong() + _state.value.initialPillQuantity
                    val fewCond = 60 * 0.2
                    if (diff < 60) {
                        if (diff > fewCond.toLong()) {
                            updateNumberOfPills(
                                containerId = container,
                                pillQuantity = diff,
                                state = "dużo"
                            )
                        } else {
                            updateNumberOfPills(
                                containerId = container,
                                pillQuantity = diff,
                                state = "mało"
                            )
                        }

                    } else if (diff > 60) {
                        val subDiff = 60 - _state.value.initialPillQuantity
                        _state.update { it.copy(pillQuantityEntry = "$subDiff") }

                        updateNumberOfPills(
                            containerId = container,
                            pillQuantity = subDiff,
                            state = "dużo"
                        )
                    } else {
                        _state.update { it.copy(pillQuantityEntryError = true) }
                    }

                }

            }
            is ContainerSettingsAction.OnAddPillsClick -> {
                if (_state.value.pillQuantityEntry.toInt() < 60) {
                    _state.update { it.copy(pillQuantityEntry = (it.pillQuantityEntry.toInt() + 1).toString()) }
                }
            }
            is ContainerSettingsAction.OnSubtractPillsClick -> {
                if (_state.value.pillQuantityEntry.toInt() > 0) {
                    _state.update { it.copy(pillQuantityEntry = (it.pillQuantityEntry.toInt() - 1).toString()) }
                }
            }
            is ContainerSettingsAction.OnConfirmEmptyClick -> {
                emptyContainer(container)
                _state.update { it.copy(isEmptyContainerDialogVisible = false) }
            }
            is ContainerSettingsAction.OnEmptyContainerClick -> {
                _state.update { it.copy(isEmptyContainerDialogVisible = true) }
            }
            is ContainerSettingsAction.OnDismissDialog -> {
                _state.update { it.copy(
                    errorMessage = null,
                    isEmptyContainerDialogVisible = false
                ) }
            }
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
                        fetchMyMedicament(currentFamilyId)
                    } else {
                        searchMedicament(familyId = currentFamilyId, partialName = text)
                    }
                }
        }
    }

    private fun fetchFamilyId() {
        viewModelScope.launch {
            sharedViewModel.userInfo
                .map { it?.familyId }
                .filter { it != 0L }
                .distinctUntilChanged()
                .onEach { familyId ->
                    if (familyId != null) {
                        currentFamilyId = familyId
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun fetchContainerSettings(containerId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchContainerSettings(containerId)
                _containerSettings.emit(result.asDomainModel())
            }.onSuccess {
                _state.update { it.copy(
                    containerSettings = _containerSettings.value,
                    isLoading = false,
                    initialSelectedMedicineId = _containerSettings.value.containerId,
                    initialSearchEntry = _containerSettings.value.medicamentName ?: "",
                    initialPillQuantity = _containerSettings.value.pillQuantity,
                    pillQuantity = _containerSettings.value.pillQuantity.toString()
                ) }
                if (_searchQuery.value.isBlank()) {
                    _searchQuery.value = _containerSettings.value.medicamentName ?: ""
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Błąd w komunikacji z serwerem."
                    )
                }
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
                _state.update { it.copy(isSearching = false) }
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
                    it.copy(isSearching = false)
                }
            }.onFailure {
                _state.update {
                    it.copy(isSearching = false, searchError = "Błąd w wyszukiwaniu.")
                }
            }
        }
    }

    private fun updateContainerMedicament(containerId: Long, medicamentId: Long) {
        viewModelScope.launch {
            runCatching {
                appRepository.updateContainerMedicament(containerId, medicamentId)
            }.onSuccess {
                fetchContainerSettings(container)
                _state.update { it.copy(successMessage = "Pomyślnie zaktualizowano!") }
            }.onFailure {
                _state.update { it.copy(errorMessage = "Błąd w komunikacji z serwerem.") }
            }
        }
    }

    private fun updateNumberOfPills(containerId: Long, pillQuantity: Long, state: String) {
        viewModelScope.launch {
            runCatching {
                appRepository.updateNumberOfPills(containerId, pillQuantity, state)
            }.onSuccess {
                fetchContainerSettings(container)
                _state.update { it.copy(successMessage = "Pomyślnie zaktualizowano!") }
            }.onFailure {
                _state.update { it.copy(errorMessage = "Błąd w komunikacji z serwerem.") }
            }
        }
    }

    private fun emptyContainer(containerId: Long) {
        viewModelScope.launch {
            runCatching {
                appRepository.emptyContainer(containerId)
            }.onSuccess {
                fetchContainerSettings(container)
            }.onFailure { error ->
                println(error)
            }
        }
    }

    private fun ContainerSettingsDto.asDomainModel(): ContainerSettings {
        return ContainerSettings(
            containerId = this.containerId,
            containerState = this.containerState,
            medicamentName = this.medicamentName,
            containerNumber = this.containerNumber,
            pillQuantity = this.pillQuantity
        )
    }

    private fun ContainerSettingsSearchQueryDto.asDomainModel(): ContainerSettingsSearchQuery {
        return ContainerSettingsSearchQuery(
            medicamentId = this.medicamentId,
            medicamentName = this.medicamentName
        )
    }
}