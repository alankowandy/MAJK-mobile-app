package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.example.majk.majk.domain.MedicineEntry

interface AddScheduleAction {
    data class OnSearchQueryChange(
        val medicamentSearch: String,
        val medicamentId: Long
    ): AddScheduleAction
    data class OnSearchExpandedChange(val isExpanded: Boolean): AddScheduleAction
    data object OnStartDateClick: AddScheduleAction
    data class OnStartDatePick(val date: Long?): AddScheduleAction
    data object OnEndDateClick: AddScheduleAction
    data class OnEndDatePick(val date: Long?): AddScheduleAction
    data object OnTimePickClick: AddScheduleAction
    data class OnTakingTimePick(val time: LocalTime): AddScheduleAction
    data object OnDayIntervalClick: AddScheduleAction
    data class OnTakingIntervalChange(val days: Int): AddScheduleAction
    data object OnPillAmountClick: AddScheduleAction
    data class OnPillAmountChange(val pillQuantity: Int): AddScheduleAction
    data class OnToggleBeforeMeal(val value: Boolean): AddScheduleAction
    data class OnToggleDuringMeal(val value: Boolean): AddScheduleAction
    data class OnToggleAfterMeal(val value: Boolean): AddScheduleAction
    data class OnNoteChange(val text: String): AddScheduleAction
    data class OnSelectedMedicineChange(val medicine: MedicineEntry): AddScheduleAction
    data object OnSaveClick: AddScheduleAction
    data object OnBackClick: AddScheduleAction
}