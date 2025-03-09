package org.example.majk.majk.presentation.majk_login.majk_signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.majk.majk.domain.AuthRepository

class MajkSignUpViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(MajkSignUpState())
    val state = _state.asStateFlow()

    fun onAction(action: MajkSignUpAction) {
        when(action) {
            is MajkSignUpAction.OnEmailChange -> {
                _state.update {
                    it.copy(emailEntry = action.email)
                }
            }
            is MajkSignUpAction.OnPasswordChange -> {
                _state.update {
                    it.copy(passwordEntry = action.password)
                }
            }
            is MajkSignUpAction.OnUsernameChange -> {
                _state.update {
                    it.copy(usernameEntry = action.username)
                }
            }
            is MajkSignUpAction.OnFamilyCodeChange -> {
                _state.update {
                    it.copy(familyCode = action.familyCode)
                }
            }
            is MajkSignUpAction.OnSignUpClick -> majkSignUp()
            else -> Unit
        }
    }

    private fun majkSignUp() {

    }
}