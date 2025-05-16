package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen

import kotlinx.datetime.LocalDate

interface DetailsAction {
    data class OnSelectDate(val date: LocalDate) : DetailsAction
    data object OnRefreshCurrentTime : DetailsAction
    data object OnScheduledMedicineClick: DetailsAction
    data object OnBackClick: DetailsAction
}