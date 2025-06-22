package org.example.majk.majk.presentation.majk_login.majk_signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.domain.repository.AuthRepository

class MajkSignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(MajkSignInState())
    val state = _state.asStateFlow()

    private val _emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")


    fun onAction(action: MajkSignInAction) {
        when(action) {
            is MajkSignInAction.OnEmailChange -> {
                _state.update {
                    it.copy(emailEntry = action.email)
                }
            }
            is MajkSignInAction.OnPasswordChange -> {
                _state.update {
                    it.copy(passwordEntry = action.password)
                }
            }
            is MajkSignInAction.OnErrorClear -> {
                _state.update {
                    it.copy(errorMessage = null)
                }
            }
            is MajkSignInAction.OnSignInClick -> {
                val emailError = _state.value.emailEntry.isBlank()
                        || !_emailRegex.matches(_state.value.emailEntry)
                val passwordError = _state.value.passwordEntry.isBlank()

                if (emailError) {
                    _state.update {
                        it.copy(
                            emailError = true
                        )
                    }
                }
                if (passwordError) {
                    _state.update {
                        it.copy(
                            passwordError = true
                        )
                    }
                }
                if (_state.value.emailError || _state.value.passwordError) {
                    _state.update {
                        it.copy(
                            errorMessage = "Nieprawidłowy format danych"
                        )
                    }
                } else {
                    majkSignIn()
                }
            }
            else -> Unit
        }
    }

    private fun majkSignIn() {
        val email = _state.value.emailEntry
        val password = _state.value.passwordEntry

        viewModelScope.launch {
            _state.update {
                it.copy(isProcessing = true)
            }

            kotlin.runCatching {
                authRepository.signIn(email, password)
            }.onSuccess {
                _state.update {
                    it.copy(isProcessing = false, isLogged = true)
                }
            }.onFailure { error ->
                if (error.message?.contains("invalid_credentials") == true) {
                    _state.update {
                        it.copy(errorMessage = "Niepoprawne dane logowania.")
                    }
                }
                println(error)
            }
        }
    }
}