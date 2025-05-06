package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

sealed interface SettingsAction {
    data class OnUsernameChange(val username: String): SettingsAction
    data class OnPermissionChange(val permission: String): SettingsAction
    data object OnConfirmClick: SettingsAction
    data object OnBackClick: SettingsAction
    data object OnDeleteClick: SettingsAction
    data object OnDismissClick: SettingsAction
}