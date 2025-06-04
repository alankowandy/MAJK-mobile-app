package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings

import androidx.lifecycle.ViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*

class AdminAuthSettingsViewModel(
    private val appRepository: AppRepository
): ViewModel() {

    private val _state = MutableStateFlow(AdminAuthSettingsState())
    val state = _state.asStateFlow()

    fun onAction(action: AdminAuthSettingsAction) {
        when (action) {
            is AdminAuthSettingsAction.OnNfcCheckedChange -> {

            }
        }
    }
}