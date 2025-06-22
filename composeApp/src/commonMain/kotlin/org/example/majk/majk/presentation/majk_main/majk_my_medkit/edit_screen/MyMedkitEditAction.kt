package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen

import kotlinx.datetime.LocalDate

interface MyMedkitEditAction {
    data class OnSearchQueryChange(val medicamentSearch: String): MyMedkitEditAction
    data class OnSearchExpandedChange(val isExpanded: Boolean): MyMedkitEditAction
    data class OnStartDatePick(val date: LocalDate): MyMedkitEditAction
    data class OnEndDatePick(val date: LocalDate): MyMedkitEditAction
    data class OnTakingTimePick(val date: LocalDate): MyMedkitEditAction
    data class OnTakingIntervalChange(val days: Int): MyMedkitEditAction
    data class OnPillAmountChange(val pillQuantity: Int): MyMedkitEditAction
    data class OnToggleBeforeMeal(val value: Boolean): MyMedkitEditAction
    data class OnToggleDuringMeal(val value: Boolean): MyMedkitEditAction
    data class OnToggleAfterMeal(val value: Boolean): MyMedkitEditAction
    data class OnNoteChange(val text: String): MyMedkitEditAction
    data class OnSaveClick(val medicamentId: Long): MyMedkitEditAction
    data object OnBackClick: MyMedkitEditAction
}