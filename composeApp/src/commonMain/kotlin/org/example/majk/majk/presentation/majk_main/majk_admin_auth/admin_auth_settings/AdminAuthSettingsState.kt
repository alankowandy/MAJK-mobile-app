package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings

import org.example.majk.majk.domain.AdminAuthSettings

data class AdminAuthSettingsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val settingsList: List<AdminAuthSettings> = emptyList(),
    val isNfcChecked: Boolean = false,
    val isRfidChecked: Boolean = false
)