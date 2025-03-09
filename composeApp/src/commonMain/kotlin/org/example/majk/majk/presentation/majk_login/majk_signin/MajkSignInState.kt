package org.example.majk.majk.presentation.majk_login.majk_signin

import org.example.majk.core.presentation.UiText

data class MajkSignInState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val isProcessing: Boolean = false,
    val isLogged: Boolean = false,
    val errorMessage: UiText? = null
)