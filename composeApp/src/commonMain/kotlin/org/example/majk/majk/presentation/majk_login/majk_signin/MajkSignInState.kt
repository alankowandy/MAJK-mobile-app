package org.example.majk.majk.presentation.majk_login.majk_signin

data class MajkSignInState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val isProcessing: Boolean = false,
    val isLogged: Boolean = false,
    val errorMessage: String? = null
)