package org.example.majk.majk.presentation.majk_login.majk_signin

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.core.domain.Result
import org.example.majk.majk.domain.AuthRepository

class MajkSignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(MajkSignInState())
    val state = _state.asStateFlow()

    val interactionSource = MutableInteractionSource()

    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage

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
            is MajkSignInAction.OnSignInClick -> majkSignIn()
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
                _state.update {
                    it.copy(errorMessage = error.message)
                }
            }
        }
    }
}