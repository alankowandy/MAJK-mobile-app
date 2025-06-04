package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen

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
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.domain.ManageFamily

class ManageFamilyViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(ManageFamilyState())
    val state = _state.asStateFlow()

    private val _users = MutableStateFlow<List<ManageFamily>>(emptyList())

    private var currentFamilyId: Long? = null

    init {
        sharedViewModel.userInfo
            .map { it?.familyId }
            .filter { it != 0L }
            .distinctUntilChanged()
            .onEach { familyId ->
                if (familyId != null) {
                    currentFamilyId = familyId
                    collectUsers(familyId)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ManageFamilyAction) {
        when (action) {
            is ManageFamilyAction.OnScheduleClick -> {}
            is ManageFamilyAction.OnSettingsClick -> {}
            is ManageFamilyAction.OnRefreshData -> {
                _state.update { it.copy(isLoading = true) }
                currentFamilyId?.let { collectUsers(it) }
            }
            is ManageFamilyAction.OnDismissDialog -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun collectUsers(familyId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                val result = appRepository.collectUsers(familyId)
                    .map { it.asDomainModel() }
                    .sortedBy { it.id }
                _users.emit(result)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        initialLoadDone = true,
                        users = _users.value
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Dane nie mogły zostać pobrane."
                    )
                }
            }
        }
    }

    private fun ManageFamilyDto.asDomainModel(): ManageFamily {
        return ManageFamily(
            id = this.userId,
            username = this.username,
            avatarColor = this.avatarColor
        )
    }
}