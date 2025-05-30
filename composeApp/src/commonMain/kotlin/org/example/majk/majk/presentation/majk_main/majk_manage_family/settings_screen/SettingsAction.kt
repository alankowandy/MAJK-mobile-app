package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import androidx.compose.ui.graphics.Color

sealed interface SettingsAction {
    data class OnUsernameChange(val username: String): SettingsAction
    data class OnPermissionChange(val permission: String): SettingsAction
    data class OnColorChange(val color: Int): SettingsAction
    data object OnConfirmClick: SettingsAction
    data object OnBackClick: SettingsAction
    data object OnDeleteClick: SettingsAction
    data object OnDeleteConfirm: SettingsAction
    data object OnDismissClick: SettingsAction
}