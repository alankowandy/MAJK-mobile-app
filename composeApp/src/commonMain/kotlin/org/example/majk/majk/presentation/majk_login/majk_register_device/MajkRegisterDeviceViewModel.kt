package org.example.majk.majk.presentation.majk_login.majk_register_device

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.majk.majk.domain.AuthRepository

class MajkRegisterDeviceViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(MajkRegisterDeviceState())
    val state = _state.asStateFlow()

    fun onAction(action: MajkRegisterDeviceAction) {
        when(action) {
            is MajkRegisterDeviceAction.OnEmailChange -> {
                _state.update {
                    it.copy(emailEntry = action.email)
                }
            }
            is MajkRegisterDeviceAction.OnPasswordChange -> {
                _state.update {
                    it.copy(passwordEntry = action.password)
                }
            }
            is MajkRegisterDeviceAction.OnUsernameChange -> {
                _state.update {
                    it.copy(usernameEntry = action.username)
                }
            }
            is MajkRegisterDeviceAction.OnDeviceCodeChange -> {
                _state.update {
                    it.copy(deviceCode = action.deviceCode)
                }
            }
            is MajkRegisterDeviceAction.OnRegisterClick -> majkRegister()
            else -> Unit
        }
    }

    private fun majkRegister() {

    }
}