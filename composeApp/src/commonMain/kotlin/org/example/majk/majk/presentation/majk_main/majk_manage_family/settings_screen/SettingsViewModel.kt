package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.example.majk.app.Route
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.core.presentation.SharedAction
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.dto.UserSettingsDto
import org.example.majk.majk.domain.UserSettings

class SettingsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val user = savedStateHandle.toRoute<Route.MajkManageFamilySettings>().userId

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _userSettings = MutableStateFlow(UserSettings())
    val userSettings = _userSettings.asStateFlow()

    init {
        fetchUserSettings(user)
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnUsernameChange -> {
                _state.update { it.copy(usernameEntry = action.username) }
            }
            is SettingsAction.OnPermissionChange -> {
                _state.update { it.copy(permissionEntry = action.permission) }
            }
            is SettingsAction.OnColorChange -> {
                _state.update { it.copy(userAvatarColor = action.color) }
                println(action.color)
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
                    val username = _state.value.usernameEntry
                    val initialPermission = _state.value.initialPermissionEntry
                    val initialUsername = _state.value.initialUsernameEntry
                    val permission = when (_state.value.permissionEntry) {
                        "Administrator" -> "admin"
                        "Użytkownik" -> "user"
                        "Ograniczony" -> "limited"
                        else -> "limited"
                    }

                    if (initialUsername != username) {
                        updateUsername(
                            userId = user,
                            username = username
                        )
                    }

                    if (initialPermission != permission) {
                        when (initialPermission) {
                            "admin" -> {
                                _state.update { it.copy(errorMessage = "Nie można zmienić dostępów pierwotnego administratora.") }
                            }
                            "user" -> {
                                updatePermission(
                                    userId = user,
                                    permission = permission
                                )
                            }
                            "limited" -> {
                                _state.update { it.copy(errorMessage = "Obecnie nie można zmienić dostępów ograniczonego profilu.") }
                            }
                        }
                    }
                }

                if (_state.value.userAvatarColor != _state.value.initialUserAvatarColor) {
                    changeAvatarColor(user, _state.value.userAvatarColor)
                }

            }
            is SettingsAction.OnDeleteClick -> {
                _state.update { it.copy(isDeleteConfirmVisible = true) }
            }
            is SettingsAction.OnDeleteConfirm -> {
                if (_state.value.initialPermissionEntry == "admin") {
                    _state.update { it.copy(errorMessage = "Nie można usunąć konta administratora.") }
                } else {
                    deleteProfile(userId = user, username = _userSettings.value.currentUsername ?: "")
                }
            }
            is SettingsAction.OnBackClick -> {}
            is SettingsAction.OnDismissClick -> {
                _state.update { it.copy(
                    errorMessage = null,
                    isDeleteConfirmVisible = false
                ) }
            }
        }
    }

    private fun fetchUserSettings(userId: Long) {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = appRepository.fetchUserSettings(userId)
                _userSettings.emit(result.asDomainModel())
            }.onSuccess {
                _state.update {
                    it.copy(
                        initialUsernameEntry = _userSettings.value.currentUsername,
                        usernameEntry = _userSettings.value.currentUsername,
                        initialPermissionEntry = _userSettings.value.currentPermission,
                        initialUserAvatarColor = _userSettings.value.avatarColor,
                        userAvatarColor = _userSettings.value.avatarColor,
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

    private fun updatePermission(
        userId: Long,
        permission: String
    ) {
        viewModelScope.launch {
            runCatching {
                appRepository.updatePermission(userId, permission)
            }.onSuccess {
                sharedViewModel.onAction(SharedAction.OnRefreshActionData)
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        errorMessage = "Błąd przy zmianie danych. Spróbuj ponownie."
                    )
                }
                println(error)
            }
        }
    }

    private fun updateUsername(userId: Long, username: String) {
        viewModelScope.launch {
            runCatching {
                appRepository.updateUsername(userId, username)
            }.onSuccess {
                sharedViewModel.onAction(SharedAction.OnRefreshActionData)
            }.onFailure { error ->
                _state.update { it.copy(
                        errorMessage = "Błąd. Spróbuj ponownie."
                    )
                }
                println(error)
            }
        }
    }

    private fun deleteProfile(userId: Long, username: String) {
        viewModelScope.launch {
            runCatching {
                appRepository.deleteUserProfile(userId, username)
            }.onSuccess {
                sharedViewModel.onAction(SharedAction.OnRefreshActionData)
            }.onFailure {
                _state.update {
                    it.copy(
                        errorMessage = "Nie można usunąć profilu. Spróbuj ponownie."
                    )
                }
            }
        }
    }

    private fun changeAvatarColor(accountId: Long, color: Int) {
        viewModelScope.launch {
            runCatching {
                appRepository.changeProfileColor(accountId, color)
            }.onSuccess {
                sharedViewModel.onAction(SharedAction.OnRefreshActionData)
            }.onFailure {
                _state.update {
                    it.copy(
                        errorMessage = "Nie można zaktualizować profilu. Spróbuj ponownie."
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
            currentUsername = this.currentUsername,
            avatarColor = this.avatarColor
        )
    }
}