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

class SettingsViewModel(
    private val appRepository: AppRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val user = savedStateHandle.toRoute<Route.MajkManageFamilySettings>().userId

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            fetchUserSettings()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _userSettings = MutableStateFlow<List<UserSettings>>(listOf())
    val userSettings = _userSettings.asStateFlow()

    private fun fetchUserSettings() {
        viewModelScope.launch {
            kotlin.runCatching {
                val result = appRepository.fetchUserSettings(user)
                _userSettings.emit(result.map { it.asDomainModel() })
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.toString()
                    )
                }
            }
            _state.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun UserSettingsDto.asDomainModel(): UserSettings {
        return UserSettings(
            currentPermission = this.currentPermission,
            currentUsername = this.currentUsername
        )
    }
}