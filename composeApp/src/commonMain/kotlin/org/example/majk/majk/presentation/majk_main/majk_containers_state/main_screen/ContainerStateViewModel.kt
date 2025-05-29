package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.dto.ContainerStateDto
import org.example.majk.majk.domain.ContainerState


class ContainerStateViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(ContainerScreenState())
    val state = _state.asStateFlow()

    private val _containersList = MutableStateFlow<List<ContainerState>>(emptyList())

    private var currentDeviceId: Long? = null

    init {
        sharedViewModel.userInfo
            .map { it?.deviceId }
            .filter { it != 0L }
            .distinctUntilChanged()
            .onEach { deviceId ->
                if (deviceId != null) {
                    currentDeviceId = deviceId
                    fetchContainers(deviceId)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ContainerStateAction) {
        when (action) {
            is ContainerStateAction.OnRefreshData -> {
                _state.update { it.copy(isLoading = true) }
                currentDeviceId?.let { fetchContainers(it) }
            }
            is ContainerStateAction.OnSettingsClick -> {

            }
            is ContainerStateAction.OnDialogClear -> {
                _state.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun fetchContainers(deviceId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = appRepository.fetchContainerState(deviceId)
                    .map { it.asDomainModel() }
                    .sortedBy { it.containerNumber }
                _containersList.emit(result)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        initialLoadDone = true,
                        containersList = _containersList.value
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Błąd w komunikacji z serwerem. Spróbuj ponownie"
                    )
                }
                println(error)
            }
        }
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