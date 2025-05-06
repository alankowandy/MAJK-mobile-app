package org.example.majk.majk.presentation.majk_main.majk_my_schedule

import kotlinx.datetime.LocalDate

interface ScheduleAction {
    data class OnSelectDate(val date: LocalDate) : ScheduleAction
    data object OnRefreshCurrentTime: ScheduleAction
}