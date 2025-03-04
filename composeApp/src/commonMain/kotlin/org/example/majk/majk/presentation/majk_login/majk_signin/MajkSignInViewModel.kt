package org.example.majk.majk.presentation.majk_login.majk_signin

import androidx.lifecycle.ViewModel
import org.example.majk.majk.domain.LogInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MajkSignInViewModel(
    private val logInRepository: LogInRepository
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
            else -> Unit
        }
    }

}