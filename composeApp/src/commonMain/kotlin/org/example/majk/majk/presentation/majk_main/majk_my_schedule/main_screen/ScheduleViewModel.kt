package org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen

import androidx.lifecycle.ViewModel
import org.example.majk.majk.domain.repository.AppRepository
import kotlinx.coroutines.flow.*


class ScheduleViewModel(
    private val appRepository: AppRepository
): ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    val state = _state.asStateFlow()

    fun onAction(action: ScheduleAction) {
        when (action) {
            is ScheduleAction.OnMonthOffsetChange -> {
                _state.update {
                    it.copy(
                        monthOffset = action.offset
                    )
                }
                println(action.offset)
            }
            is ScheduleAction.OnRefreshCurrentTime -> {

            }
            is ScheduleAction.OnSelectDate -> {

            }
        }
    }
}