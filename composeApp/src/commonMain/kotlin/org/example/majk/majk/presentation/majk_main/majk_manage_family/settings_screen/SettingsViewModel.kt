package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.example.majk.app.Route
import org.example.majk.majk.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.data.dto.UserSettingsDto
import org.example.majk.majk.domain.UserSettings
import org.example.majk.majk.presentation.majk_main.majk_manage_family.ManageFamilySharedViewModel

class SettingsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle,
    private val manageFamilySharedViewModel: ManageFamilySharedViewModel
): ViewModel() {

    private val user = savedStateHandle.toRoute<Route.MajkManageFamilySettings>().userId

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            fetchUserSettings(user)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _userSettings = MutableStateFlow(UserSettings())
    val userSettings = _userSettings.asStateFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        usernameEntry = action.username
                    )
                }
            }
            is SettingsAction.OnPermissionChange -> {
                _state.update {
                    it.copy(
                        permissionEntry = action.permission
                    )
                }
            }
            is SettingsAction.OnConfirmClick -> {
                if (_state.value.usernameEntry.isBlank()) {
                    _state.update {
                        it.copy(
                            usernameError = true,
                            errorMessage = "Nazwa użytkownika nie może być pusta."
                        )
                    }
                } else {
                    var username = _state.value.usernameEntry
                    var permission = when (_state.value.permissionEntry) {
                        "Administrator" -> "admin"
                        "Użytkownik" -> "user"
                        "Ograniczony" -> "limited"
                        else -> "limited"
                    }

                    updateUserSettings(
                        userId = user,
                        username = username,
                        permission = permission
                    )
                }

            }
            is SettingsAction.OnDeleteClick -> {
                if (userSettings.value.currentPermission == "admin") {
                    _state.update {
                        it.copy(
                            errorMessage = "Nie można usunąć konta administratora."
                        )
                    }
                } else {
                    manageFamilySharedViewModel.switchRefresh(true)
                    deleteProfile(userId = user, username = _userSettings.value.currentUsername ?: "")
                }
            }
            is SettingsAction.OnBackClick -> {

            }
            is SettingsAction.OnDismissClick -> {
                _state.update {
                    it.copy(
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun fetchUserSettings(
        userId: Long
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = appRepository.fetchUserSettings(userId)
                _userSettings.emit(result.asDomainModel())
            }.onSuccess {
                _state.update {
                    it.copy(
                        usernameEntry = _userSettings.value.currentUsername ?: "",
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toString()
                    )
                }
            }
        }
    }

    private fun updateUserSettings(
        userId: Long,
        username: String,
        permission: String
    ) {
        viewModelScope.launch {
            runCatching {
                appRepository.updateUserSettings(userId, username, permission)
            }.onFailure {
                _state.update {
                    it.copy(
                        errorMessage = "Błąd przy zmianie danych. Spróbuj ponownie."
                    )
                }
            }
        }
    }

    private fun deleteProfile(
        userId: Long,
        username: String
    ) {
        viewModelScope.launch {
            runCatching {
                appRepository.deleteUserProfile(userId, username)
            }.onFailure {
                _state.update {
                    it.copy(
                        errorMessage = "Nie można usunąć profilu. Spróbuj ponownie."
                    )
                }
            }
        }
    }

    private fun UserSettingsDto.asDomainModel(): UserSettings {
        _state.update {
            it.copy(
                permissionEntry = when (this.currentPermission) {
                    "admin" -> "Administrator"
                    "user" -> "Użytkownik"
                    "limited" -> "Ograniczony"
                    else -> ""
                }
            )
        }
        return UserSettings(
            currentPermission = this.currentPermission,
            currentUsername = this.currentUsername
        )
    }
}