package org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*
import org.example.majk.core.presentation.SharedViewModel


class ScheduleViewModel(
    private val appRepository: AppRepository,
    private val sharedViewModel: SharedViewModel
): ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    val state = _state.asStateFlow()

    init {
        fetchAccountId()
    }

    fun onAction(action: ScheduleAction) {
        when (action) {
            is ScheduleAction.OnMonthOffsetChange -> {
                _state.update { it.copy(monthOffset = action.offset) }
            }
            is ScheduleAction.OnRefreshCurrentTime -> {}
            is ScheduleAction.OnSelectDate -> {}
            is ScheduleAction.OnMedicineListClick -> {}
        }
    }

    private fun fetchAccountId() {
        sharedViewModel.userInfo
            .filterNotNull()
            .map { it.accountId }
            .distinctUntilChanged()
            .onEach { accountId ->
                if (accountId != null) {
                    _state.update { it.copy(currentAccountId = accountId) }
                }
            }
            .launchIn(viewModelScope)
    }
}