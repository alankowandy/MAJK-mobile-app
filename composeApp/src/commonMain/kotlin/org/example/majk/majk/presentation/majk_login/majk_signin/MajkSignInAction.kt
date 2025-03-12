package org.example.majk.majk.presentation.majk_login.majk_signin

sealed interface MajkSignInAction {
    data class OnEmailChange(val email: String): MajkSignInAction
    data class OnPasswordChange(val password: String): MajkSignInAction
    data object OnErrorClear: MajkSignInAction
    data object OnSignInClick: MajkSignInAction
    data object OnBackClick: MajkSignInAction
}