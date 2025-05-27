package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.example.majk.majk.domain.ContainerSettingsSearchQuery
import org.example.majk.majk.domain.MedicineEntry

data class AddScheduleState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val medicine: MedicineEntry? = null,
    val isSearchExpanded: Boolean = false,
    val isSearching: Boolean = false,
    val searchResult: List<ContainerSettingsSearchQuery> = emptyList(),
    val selectedMedicamentId: Long = 0L,
    val isStartDateVisible: Boolean = false,
    val startDateValue: LocalDate? = null,
    val isEndDateVisible: Boolean = false,
    val endDateValue: LocalDate? = null,
    val isTimePickerVisible: Boolean = false,
    val time: LocalTime? = null,
    val isIntervalDropdownVisible: Boolean = false,
    val intervalDays: Int = 1,
    val isPillAmountDropdownVisible: Boolean = false,
    val pillAmount: Int = 1,
    val beforeMeal: Boolean = false,
    val duringMeal: Boolean = false,
    val afterMeal: Boolean = false,
    val note: String = "",
    val familyIdByAccountId: Long? = 0L
)
