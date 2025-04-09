package org.example.majk.majk.presentation.majk_main.majk_add_profile

import androidx.lifecycle.ViewModel
import org.example.majk.majk.domain.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddProfileViewModel(
    private val appRepository: AppRepository
): ViewModel() {

    private val _state = MutableStateFlow(AddProfileState())
    val state = _state.asStateFlow()

    fun onAction(action: AddProfileAction) {
        when(action) {
            is AddProfileAction.OnUsernameChange -> {
                _state.update {
                    it.copy(usernameEntry = action.username)
                }
            }
        }
    }
}