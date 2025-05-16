package org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen

import kotlinx.datetime.LocalDate

interface ScheduleAction {
    data class OnSelectDate(val date: LocalDate) : ScheduleAction
    data object OnRefreshCurrentTime: ScheduleAction
    data class OnMonthOffsetChange(val offset: Int): ScheduleAction
}