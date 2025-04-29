package org.example.majk.majk.presentation.majk_login.majk_register_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.domain.AuthRepository

class MajkRegisterDeviceViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(MajkRegisterDeviceState())
    val state = _state.asStateFlow()

    private val _emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val _emailError = _state.value.emailEntry.isBlank()
            || !_emailRegex.matches(_state.value.emailEntry)
    private val _usernameError = _state.value.usernameEntry.isBlank()
    private val _passwordError = _state.value.passwordEntry.isBlank()
    private val _deviceCodeError = _state.value.deviceCode.isBlank()

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
            is MajkRegisterDeviceAction.OnErrorClear -> {
                _state.update {
                    it.copy(errorMessage = null)
                }
            }
            is MajkRegisterDeviceAction.OnRegisterClick -> {
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
                if (_deviceCodeError) {
                    _state.update {
                        it.copy(
                            deviceCodeError = true,
                        )
                    }
                }
                if (_state.value.usernameError || _state.value.emailError
                    || _state.value.passwordError || _state.value.deviceCodeError) {
                    _state.update {
                        it.copy(
                            errorMessage = "Nieprawidłowy format danych"
                        )
                    }
                } else {
                    majkRegister()
                }
            }
            else -> Unit
        }
    }

    private fun majkRegister() {
        val email = _state.value.emailEntry
        val username = _state.value.usernameEntry
        val password = _state.value.passwordEntry
        val deviceCode = _state.value.deviceCode
        var isDeviceCodeCorrect = false


        viewModelScope.launch {

        }
    }
}