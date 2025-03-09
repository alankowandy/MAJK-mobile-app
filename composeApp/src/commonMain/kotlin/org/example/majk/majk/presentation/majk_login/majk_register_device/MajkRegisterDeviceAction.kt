package org.example.majk.majk.presentation.majk_login.majk_register_device

interface MajkRegisterDeviceAction {
    data class OnEmailChange(val email: String): MajkRegisterDeviceAction
    data class OnPasswordChange(val password: String): MajkRegisterDeviceAction
    data class OnUsernameChange(val username: String): MajkRegisterDeviceAction
    data class OnDeviceCodeChange(val deviceCode: String): MajkRegisterDeviceAction
    data object OnRegisterClick: MajkRegisterDeviceAction
    data object OnBackClick: MajkRegisterDeviceAction
}