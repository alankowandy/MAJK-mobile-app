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
    val containerSettings = _containerSettings.asStateFlow()

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
            is ContainerSettingsAction.OnPillQuantityChange -> {
                _state.update {
                    it.copy(pillQuantityEntry = action.pillQuantity)
                }
            }
            is ContainerSettingsAction.OnSearchExpandedChange -> {
                _state.update {
                    it.copy(isSearchExpanded = action.isExpanded)
                }
            }
            is ContainerSettingsAction.OnEmptyContainerClick -> {
                _state.update {
                    it.copy(pillQuantityEntry = 0)
                }

            }
            is ContainerSettingsAction.OnConfirmClick -> {
                if (_searchQuery.value != _state.value.initialSearchEntry
                    && _state.value.selectedMedicamentId != -1L) {
                    updateContainerMedicament(
                        containerId = container,
                        medicamentId = _state.value.selectedMedicamentId
                    )
                } else {
                    _state.update {
                        it.copy(searchError = "Wybrany lek jest niepoprawny.")
                    }
                }

                if (_state.value.pillQuantityEntry != _state.value.initialPillQuantity) {
                    updateNumberOfPills(
                        containerId = container,
                        pillQuantity = _state.value.pillQuantityEntry.toDouble()
                    )
                }
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
                    isLoading = false,
                    initialSearchEntry = _containerSettings.value.medicamentName,
                    pillQuantityEntry = _containerSettings.value.pillQuantity.toInt()
                ) }
                _searchQuery.value = _containerSettings.value.medicamentName
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Błąd w komunikacji z serwerem."
                    )
                }
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
                _state.update {
                    it.copy(successMessage = "Pomyślnie zaktualizowano!")
                }
            }.onFailure {
                _state.update {
                    it.copy(errorMessage = "Błąd w komunikacji z serwerem.")
                }
            }
        }
    }

    private fun updateNumberOfPills(containerId: Long, pillQuantity: Double) {
        viewModelScope.launch {
            runCatching {
                appRepository.updateNumberOfPills(containerId, pillQuantity)
            }.onSuccess {
                _state.update {
                    it.copy(successMessage = "Pomyślnie zaktualizowano!")
                }
            }.onFailure {
                _state.update {
                    it.copy(errorMessage = "Błąd w komunikacji z serwerem.")
                }
            }
        }
    }

    private fun ContainerSettingsDto.asDomainModel(): ContainerSettings {
        return ContainerSettings(
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