package org.example.majk.majk.presentation.majk_login.majk_signup

interface MajkSignUpAction {
    data class OnEmailChange(val email: String): MajkSignUpAction
    data class OnPasswordChange(val password: String): MajkSignUpAction
    data class OnUsernameChange(val username: String): MajkSignUpAction
    data class OnFamilyCodeChange(val familyCode: String): MajkSignUpAction
    data object OnSignUpClick: MajkSignUpAction
    data object OnBackClick: MajkSignUpAction
}