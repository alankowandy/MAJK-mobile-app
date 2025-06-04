package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.example.majk.majk.data.dto.AdminAuthDto
import org.example.majk.majk.domain.AdminAuthUsers
import org.example.majk.majk.domain.repository.AppRepository
import org.example.majk.platform.NfcCapability


class AdminAuthViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(
        AdminAuthState(
        isHostCardEmulationAvailable = NfcCapability
            .isHostCardEmulationAvailable
    )
    )
    val state = _state.asStateFlow()

    private val _users = MutableStateFlow<List<AdminAuthUsers>>(emptyList())

    init {
        sharedViewModel.userInfo
            .map { it?.familyId }
            .filter { it != 0L }
            .distinctUntilChanged()
            .onEach { familyId ->
                if (familyId != null) {
                    collectUsers(familyId)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: AdminAuthAction) {
        when (action) {
            is AdminAuthAction.OnNfcClick -> {
                _users.update { user ->
                    user.map { entry ->
                        if (action.accountId == entry.id) {
                            entry.copy(isNfcChecked = !entry.isNfcChecked)
                        } else {
                            entry.copy(isNfcChecked = false)
                        }
                    }
                }
                _state.update { it.copy(users = _users.value) }

                if (_state.value.isHostCardEmulationAvailable) {
                    println(_state.value.isHostCardEmulationAvailable.toString())
                } else {
                    _state.update { it.copy(
                        errorMessage = "Funkcja dostępna tylko na Androidzie. " +
                                "Twoje urządzenie nie obsługuje emulacji karty NFC."
                    ) }
                }
            }
            is AdminAuthAction.OnRfidClick -> {
                _users.update { user ->
                    user.map { entry ->
                        if (action.accountId == entry.id) {
                            entry.copy(isRfidChecked = !entry.isRfidChecked)
                        } else {
                            entry
                        }
                    }
                }
                _state.update { it.copy(users = _users.value) }
            }
            is AdminAuthAction.OnDismissError -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun collectUsers(familyId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                val result = appRepository.collectUsersAdminAuth(familyId)
                    .map { it.asDomainModel() }
                    .sortedBy { it.id }
                _users.emit(result)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
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
                println(_state.value.errorMessage)
            }
        }
    }

    private fun AdminAuthDto.asDomainModel(): AdminAuthUsers {
        return AdminAuthUsers(
            id = this.userId,
            username = this.username,
            avatarColor = this.avatarColor
        )
    }
}