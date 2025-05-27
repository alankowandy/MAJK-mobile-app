package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list

data class ScheduledMedicineListState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val noteEntry: String? = null,
    val saveStatuses: Map<Long, SaveStatus> = emptyMap()
)

enum class SaveStatus { Idle, Saving, Success }