package org.example.majk.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.status.SessionStatus
import org.example.majk.majk.domain.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SharedViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    val sessionStatus = authRepository.sessionStatus().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SessionStatus.NotAuthenticated(false)
    )



    suspend fun signOut() {
        authRepository.signOut()
    }
}