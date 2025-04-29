package org.example.majk.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.status.SessionStatus
import org.example.majk.majk.domain.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.majk.core.data.dto.SharedStateDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance

class SharedViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow(SharedState())
    val state = _state.asStateFlow()

    val sessionStatus = authRepository.sessionStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SessionStatus.Initializing
    )

    private val _userInfo = MutableStateFlow<SharedState?>(null)
    val userInfo = _userInfo.asStateFlow()

    init {
        viewModelScope.launch {
            sessionStatus
                .filterIsInstance<SessionStatus.Authenticated>()
                .collect { auth ->
                    val id = auth.session.user?.id ?: return@collect
                    fetchUserInfo(id)
                }
        }
    }

    private fun fetchUserInfo(authId: String) {
        viewModelScope.launch {
            runCatching {
                val result = authRepository.fetchProfileDetails(authId)
                _userInfo.emit(result.asDomainModel())
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        errorMessage = "Błąd przy pobieraniu danych"
                    )
                }
                println(error.message)
            }
        }
    }

    suspend fun signOut() {
        authRepository.signOut()
    }

    private fun SharedStateDto.asDomainModel(): SharedState {
        return SharedState(
            accountId = this.accountId,
            username = this.username,
            familyId = this.familyId,
            deviceId = this.deviceId
        )
    }

}