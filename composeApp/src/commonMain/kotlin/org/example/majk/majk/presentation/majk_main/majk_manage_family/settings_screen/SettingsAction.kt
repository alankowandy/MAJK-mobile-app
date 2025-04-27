package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

sealed interface SettingsAction {
    data object OnConfirmClick: SettingsAction
    data object OnBackClick: SettingsAction
    data object OnDeleteClick: SettingsAction
}