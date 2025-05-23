package org.example.majk.majk.presentation.majk_main.majk_add_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.majk.core.presentation.SharedAction
import org.example.majk.core.presentation.SharedViewModel

class AddProfileViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(AddProfileState())
    val state = _state.asStateFlow()

    private var userFamilyId: Long = 0L
    private var userUuid: String = ""

    init {
        sharedViewModel.userInfo
            .filter { it != null }
            .distinctUntilChanged()
            .onEach { userInfo ->
                userFamilyId = userInfo?.familyId ?: 0L
                userUuid = userInfo?.uuid ?: ""
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: AddProfileAction) {
        when(action) {
            is AddProfileAction.OnUsernameChange -> {
                _state.update {
                    it.copy(usernameEntry = action.username)
                }
            }
            is AddProfileAction.OnAddProfileClick -> {
                val username = _state.value.usernameEntry
                insertLimitedProfile(
                    username = username,
                    uuid = userUuid,
                    familyId = userFamilyId
                )
                _state.update {
                    it.copy(
                        usernameEntry = ""
                    )
                }
            }
            is AddProfileAction.OnDialogClear -> {
                _state.update {
                    it.copy(
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }
        }
    }

    private fun insertLimitedProfile(
        username: String,
        uuid: String,
        familyId: Long
    ) {
        viewModelScope.launch {
            if (username.isBlank()) {
                _state.update {
                    it.copy(
                        usernameError = true,
                        errorMessage = "Nazwa profilu nie może być pusta."
                    )
                }
            } else {
                kotlin.runCatching {
                    appRepository.insertLimitedProfile(username, uuid, familyId)
                }.onSuccess {
                    sharedViewModel.onAction(SharedAction.OnRefreshActionData)
                    _state.update {
                        it.copy(
                            usernameError = false,
                            successMessage = "Pomyślnie dodano profil!"
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            usernameError = false,
                            errorMessage = "Błąd w dodawaniu profilu."
                        )
                    }
                    println(error)
                }
            }
        }
    }
}