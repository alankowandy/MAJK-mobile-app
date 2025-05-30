package org.example.majk.majk.presentation.majk_main.majk_home

import androidx.lifecycle.ViewModel
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*


class HomeViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnDismissDialog -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }
}