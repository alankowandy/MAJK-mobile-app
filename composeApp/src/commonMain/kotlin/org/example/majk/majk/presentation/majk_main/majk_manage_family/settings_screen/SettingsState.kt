package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import org.example.majk.majk.domain.UserSettings

data class SettingsState(
    val usernameEntry: String = "",
    val permissionEntry: String = "",
    val userSettings: UserSettings? = null,
    val isLoading: Boolean = true,
    val usernameError: Boolean = false,
    val errorMessage: String? = null
)