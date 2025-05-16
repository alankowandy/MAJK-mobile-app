package org.example.majk.majk.presentation.majk_login.majk_register_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.majk.domain.AuthRepository

class MajkRegisterDeviceViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(MajkRegisterDeviceState())
    val state = _state.asStateFlow()

    private val _emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val _passwordRegex = Regex("^.{6,}$")

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
                if (_state.value.usernameEntry.isBlank()) {
                    _state.update {
                        it.copy(
                            usernameError = true
                        )
                    }
                }
                if (_state.value.emailEntry.isBlank()
                    || !_emailRegex.matches(_state.value.emailEntry)) {
                    _state.update {
                        it.copy(
                            emailError = true
                        )
                    }
                }
                if (_state.value.passwordEntry.isBlank()
                    || !_passwordRegex.matches(_state.value.passwordEntry)) {
                    _state.update {
                        it.copy(
                            passwordError = true
                        )
                    }
                }
                if (_state.value.deviceCode.isBlank()) {
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
                            errorMessage = "Nieprawidłowy format danych lub hasło poniżej 6 znaków"
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
        val deviceCode = _state.value.deviceCode.toLong()
        var isDeviceCodeCorrect = false
        var isSignedUp = false
        var isUserInserted = false


        viewModelScope.launch {
            runCatching {
                val result = authRepository.checkDeviceCode(deviceCode)
                isDeviceCodeCorrect = result.deviceIdExists ?: false
                if (!isDeviceCodeCorrect) {
                    _state.update {
                        it.copy(
                            errorMessage = "Urządzenie o podanym kodzie nie istnieje."
                        )
                    }
                }
                println(isDeviceCodeCorrect)
            }.onFailure { error ->
                println(error)
                _state.update {
                    it.copy(
                        errorMessage = "Błąd w pobieraniu danych."
                    )
                }
            }

            if (isDeviceCodeCorrect) {
                runCatching {
                    authRepository.signUp(email = email, password = password)
                }.onSuccess {
                    isSignedUp = true
//                    runCatching {
//                        authRepository.signIn(email = email, password = password)
//                    }.onSuccess {
//                        val sessionStatus = authRepository.sessionStatus().stateIn(
//                            scope = viewModelScope,
//                            started = SharingStarted.Eagerly,
//                            initialValue = SessionStatus.Initializing
//                        )
//                        sessionStatus.filterIsInstance<SessionStatus.Authenticated>()
//                            .collect { auth ->
//                                val id = auth.session.user?.id ?: return@collect
//                                authRepository.insertAdminProfile(
//                                    username = username,
//                                    deviceCode = deviceCode,
//                                    accountId = id
//                                )
//                            }
//                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = "Błąd przy logowaniu. Spróbuj ponownie."
                        )
                    }
                    println(error)
                }
            }

            if (isSignedUp) {
                runCatching {
                    authRepository.insertAdminProfile(
                        username = username,
                        deviceCode = deviceCode,
                        email = email
                    )
                }.onSuccess {
                    isUserInserted = true
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = "Błąd"
                        )
                    }
                    println(error)
                }
            }

            if (isUserInserted) {
                runCatching {
                    authRepository.signIn(email, password)
                }.onFailure { error ->
                    println(error)
                }
            }
        }
    }
}