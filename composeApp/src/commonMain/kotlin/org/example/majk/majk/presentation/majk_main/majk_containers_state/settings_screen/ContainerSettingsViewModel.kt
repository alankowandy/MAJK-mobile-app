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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import org.example.majk.majk.data.dto.ContainerSettingsDto
import org.example.majk.majk.domain.ContainerSettings

class ContainerSettingsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val container = savedStateHandle.toRoute<Route.MajkContainerSettings>().containerId

    private val _state = MutableStateFlow(ContainerSettingsState())
    val state = _state.asStateFlow()

    private val _containerSettings = MutableStateFlow(ContainerSettings())
    val containerSettings = _containerSettings.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        fetchContainerSettings(containerId = container)
        observeSearchText()
    }

    fun onAction(action: ContainerSettingsAction) {
        when (action) {
            is ContainerSettingsAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchEntry = action.medicamentSearch)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchText() {
        viewModelScope.launch {
            searchQuery
                .debounce(500L)
                .onEach { _state.update { it.copy(isSearching = true) } }
                .collect { text ->
                    if (text.isBlank()) {

                    } else {

                    }
                }
        }
    }

    private fun fetchContainerSettings(containerId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchContainerSettings(containerId)
                _containerSettings.emit(result.asDomainModel())
            }.onSuccess {
                _state.update { it.copy(isLoading = false) }
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

    private fun ContainerSettingsDto.asDomainModel(): ContainerSettings {
        return ContainerSettings(
            medicamentName = this.medicamentName,
            containerNumber = this.containerNumber,
            pillQuantity = this.pillQuantity
        )
    }
}