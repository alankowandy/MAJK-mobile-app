package org.example.majk.majk.presentation.majk_login.majk_start

interface MajkStartAction {
    data object OnSignInClick: MajkStartAction
    data object OnSignUpClick: MajkStartAction
    data object OnRegisterDeviceClick: MajkStartAction
}