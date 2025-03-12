package org.example.majk.majk.presentation.majk_login.majk_signup

import org.example.majk.core.presentation.UiText

data class MajkSignUpState(
    val emailEntry: String = "",
    val passwordEntry: String = "",
    val usernameEntry: String = "",
    val familyCode: String = "",
    val isProcessing: Boolean = false,
    val errorMessage: String? = null
)