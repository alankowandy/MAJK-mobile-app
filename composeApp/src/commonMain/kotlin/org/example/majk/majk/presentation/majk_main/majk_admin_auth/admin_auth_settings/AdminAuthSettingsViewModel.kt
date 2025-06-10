package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.majk.app.Route
import org.example.majk.majk.data.dto.AdminAuthSettingsDto
import org.example.majk.majk.domain.AdminAuthSettings

class AdminAuthSettingsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val selectedUser = savedStateHandle.toRoute<Route.MajkAdminAuthSettings>().accountId

    private val _state = MutableStateFlow(AdminAuthSettingsState())
    val state = _state.asStateFlow()

    private val _userSettings = MutableStateFlow<List<AdminAuthSettings>>(emptyList())

    init {
        fetchUserSettings(selectedUser)
    }

    fun onAction(action: AdminAuthSettingsAction) {
        when (action) {
            is AdminAuthSettingsAction.OnNfcCheckedChange -> {

            }
        }
    }

    private fun fetchUserSettings(profileId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = appRepository.fetchAuthUserSettings(profileId)
                _userSettings.emit(result.map { it.asDomainModel() })
            }.onSuccess {
                if (_userSettings.value.any { setting -> setting.id == null }) {
                    _state.update { it.copy(
                        isNfcChecked = false,
                        isRfidChecked = false,
                        isLoading = false
                    ) }
                } else if (_userSettings.value.any { it.type == "NFC" }) {
                    _state.update { it.copy(isNfcChecked = true, isLoading = false) }
                } else if (_userSettings.value.any { it.type == "RFID" }) {
                    _state.update { it.copy(isRfidChecked = true, isLoading = false) }
                }
            }.onFailure { error ->
                _state.update { it.copy(
                    isLoading = false,
                    errorMessage = "Błąd"
                ) }
                println(error)
            }
        }
    }

    private fun AdminAuthSettingsDto.asDomainModel(): AdminAuthSettings {
        return AdminAuthSettings(
            id = this.id,
            type = this.type,
            code = this.code,
            username = this.username
        )
    }

}