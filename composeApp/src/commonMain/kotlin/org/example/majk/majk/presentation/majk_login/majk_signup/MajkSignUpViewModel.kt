package org.example.majk.majk.presentation.majk_login.majk_signup

import androidx.compose.runtime.remember
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

    private val _emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val _emailError = _state.value.emailEntry.isBlank()
            || !_emailRegex.matches(_state.value.emailEntry)
    private val _usernameError = _state.value.usernameEntry.isBlank()
    private val _passwordError = _state.value.passwordEntry.isBlank()
    private val _familyCodeError = _state.value.familyCode.isBlank()

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
            is MajkSignUpAction.OnSignUpClick -> {
                if (_usernameError) {
                    _state.update {
                        it.copy(
                            usernameError = true
                        )
                    }
                }
                if (_emailError) {
                    _state.update {
                        it.copy(
                            emailError = true
                        )
                    }
                }
                if (_passwordError) {
                    _state.update {
                        it.copy(
                            passwordError = true
                        )
                    }
                }
                if (_familyCodeError) {
                    _state.update {
                        it.copy(
                            familyCodeError = true
                        )
                    }
                }
                if (_state.value.usernameError || _state.value.emailError
                    || _state.value.passwordError || _state.value.familyCodeError) {
                    _state.update {
                        it.copy(
                            errorMessage = "Nieprawidłowy format danych"
                        )
                    }
                } else {
                    majkSignUp()
                }
            }
            else -> Unit
        }
    }

    private fun majkSignUp() {
        val email = _state.value.emailEntry
        val password = _state.value.passwordEntry
        val username = _state.value.usernameEntry
        val familyCode = _state.value.familyCode.toLong()
        var isSignUpReady = false
        var familyCodeExists = false

        viewModelScope.launch {
            runCatching {
                val result = authRepository.checkFamilyCode(familyCode)
                familyCodeExists = result.familyCodeExists ?: false
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
                    authRepository.insertNewUsername(username, familyCode)
                }.onSuccess {
                    isSignUpReady = true
                }.onFailure { error ->
                    _state.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            }

            if (isSignUpReady) {
                runCatching {
                    authRepository.signUp(email, password)
                }.onFailure { error ->
                    _state.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            }
        }
    }
}