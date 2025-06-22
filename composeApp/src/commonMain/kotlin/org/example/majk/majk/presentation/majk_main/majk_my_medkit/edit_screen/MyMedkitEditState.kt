package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen

import kotlinx.datetime.LocalDate

data class MyMedkitEditState(
    val currentAccountId: Long = 0,
    val isSearching: Boolean = true,
    val isSearchExpanded: Boolean = false,
    val initialSearchEntry: String = "",
    val selectedMedicamentId: Long = 0,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val takingHour: LocalDate? = null,
    val takingInterval: Int = 1,
    val pillTakingAmount: Int = 1,
    val beforeMeal: Boolean = false,
    val duringMeal: Boolean = false,
    val afterMeal: Boolean = false,
    val note: String = "",
    val errorMessage: String? = null
)
