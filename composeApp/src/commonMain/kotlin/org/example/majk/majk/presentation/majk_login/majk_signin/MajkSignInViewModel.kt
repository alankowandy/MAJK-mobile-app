package org.example.majk.majk.presentation.majk_login.majk_signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.AuthApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MajkSignInViewModel(
    private val authApi: AuthApi
): ViewModel() {

    private val _state = MutableStateFlow(MajkSignInState())
    val state = _state.asStateFlow()

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
            is MajkSignInAction.OnSignInClick -> majkSignIn()
            else -> Unit
        }
    }

    private fun majkSignIn() {
        val email = _state.value.emailEntry

        viewModelScope.launch {
            _state.update {
                it.copy(isProcessing = true)
            }
        }
    }
}