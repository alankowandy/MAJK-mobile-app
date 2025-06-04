package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen

import org.example.majk.majk.domain.UserSettings

data class SettingsState(
    val initialUsernameEntry: String = "",
    val usernameEntry: String = "",
    val initialPermissionEntry: String = "",
    val permissionEntry: String = "",
    val initialUserAvatarColor: Int = 0,
    val userAvatarColor: Int = 0,
    val userSettings: UserSettings? = null,
    val isLoading: Boolean = true,
    val usernameError: Boolean = false,
    val errorMessage: String? = null,
    val isDeleteConfirmVisible: Boolean = false
)