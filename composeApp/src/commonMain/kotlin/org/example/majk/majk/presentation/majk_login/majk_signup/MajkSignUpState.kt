package org.example.majk.majk.presentation.majk_login.majk_signup

data class MajkSignUpState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val usernameEntry: String = "",
    val familyCode: String = "",
    val familyCodeExists: Boolean? = false,
    val isProcessing: Boolean = false,
    val errorMessage: String? = null,
    val usernameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val familyCodeError: Boolean = false
)