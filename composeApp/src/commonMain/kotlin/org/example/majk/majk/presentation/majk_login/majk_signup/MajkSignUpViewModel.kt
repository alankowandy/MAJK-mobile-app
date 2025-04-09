package org.example.majk.majk.presentation.majk_login.majk_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.data.dto.FamilyCodeDto
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
            is MajkSignUpAction.OnErrorClear -> {
                _state.update {
                    it.copy(errorMessage = null)
                }
            }
            is MajkSignUpAction.OnSignUpClick -> majkSignUp()
            else -> Unit
        }
    }

    private fun majkSignUp() {
        val email = _state.value.emailEntry
        val password = _state.value.passwordEntry
        val username = _state.value.usernameEntry
        val familyCode = _state.value.familyCode.toLong()
        var signUpComplete = false
        var familyCodeExists = false

        viewModelScope.launch {
            runCatching {
                val result = authRepository.checkFamilyCode(familyCode)
                familyCodeExists = result.familyCodeExists ?: false
                println(familyCodeExists)
                _state.update {
                    it.copy(familyCodeExists = result.familyCodeExists)
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(errorMessage = error.message)
                }
            }

            if (familyCodeExists) {
                runCatching {
                    authRepository.signUp(email, password)
                }.onSuccess {
                    signUpComplete = true
                }.onFailure { error ->
                    _state.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            }

            if (signUpComplete) {
                runCatching {
                    authRepository.insertNewUsername(username, familyCode)
                }
            }
        }
    }
}