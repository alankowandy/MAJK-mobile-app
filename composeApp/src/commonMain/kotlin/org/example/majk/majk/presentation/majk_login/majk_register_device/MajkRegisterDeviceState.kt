package org.example.majk.majk.presentation.majk_login.majk_register_device

import org.example.majk.core.presentation.UiText

data class MajkRegisterDeviceState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val usernameEntry: String = "",
    val deviceCode: String = "",
    val isProcessing: Boolean = false,
    val errorMessage: UiText? = null
)