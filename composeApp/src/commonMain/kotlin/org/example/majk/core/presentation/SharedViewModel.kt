package org.example.majk.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.status.SessionStatus
import org.example.majk.majk.domain.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.majk.core.data.dto.SharedStateDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import org.example.majk.core.data.dto.FamilyUsersDto
import org.example.majk.core.domain.FamilyUsers
import org.example.majk.core.domain.SharedState

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

    private val _familyUsers = MutableStateFlow<List<FamilyUsers>>(emptyList())
    val familyUsers = _familyUsers.asStateFlow()

    private var id: String? = ""
    private var email: String? = ""
    private var currentFamilyId: Long = 0

    init {
        filterSessionStatus()
    }

    fun onAction(action: SharedAction) {
        when (action) {
            is SharedAction.OnExpandAction -> {
                _state.update {
                    it.copy(isActionExpanded = action.isExpanded)
                }
            }
            is SharedAction.OnRefreshActionData -> {
                fetchFamilyUsers(currentFamilyId)
                println("executed fetch")
            }
        }
    }

    private fun filterSessionStatus() {
        viewModelScope.launch {
            sessionStatus
                .filterIsInstance<SessionStatus.Authenticated>()
                .map { status ->
                    id = status.session.user?.id ?: return@map null
                    email = status.session.user?.email ?: return@map null
                    id to email
                }
                .filterNotNull()
                .distinctUntilChanged()
                .onEach { (id, email) ->
                    if (email != null && id != null) {
                        fetchUserInfo(email = email, authId = id)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun fetchUserInfo(email: String, authId: String) {
        viewModelScope.launch {
            runCatching {
                val result = authRepository.fetchProfileDetails(email = email, authId = authId)
                _userInfo.emit(result.asDomainModel())
            }.onSuccess {
                currentFamilyId = _userInfo.value?.familyId!!
                _userInfo.value?.familyId?.let { fetchFamilyUsers(it) }
                println(_userInfo.value!!.accountId)
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

    private fun fetchFamilyUsers(familyId: Long) {
        viewModelScope.launch {
            runCatching {
                val result = authRepository.fetchFamilyUsers(familyId)
                _familyUsers.emit(result.map { it.asDomainModel() })
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        actionErrorMessage = "Błąd w komunikacji z serwerem."
                    )
                }
                println(error)
            }
        }
    }

    suspend fun signOut() {
        authRepository.signOut()
    }

    private fun SharedStateDto.asDomainModel(): SharedState {
        return SharedState(
            uuid = id,
            accountId = this.accountId,
            username = this.username,
            familyId = this.familyId,
            deviceId = this.deviceId,
            permission = this.permission,
            email = email
        )
    }

    private fun FamilyUsersDto.asDomainModel(): FamilyUsers {
        return FamilyUsers(
            userId = this.userId,
            username = this.username,
            permission = this.permission
        )
    }

}