package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import org.example.majk.majk.domain.UserSettings

data class SettingsState(
    val userSettings: UserSettings? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)