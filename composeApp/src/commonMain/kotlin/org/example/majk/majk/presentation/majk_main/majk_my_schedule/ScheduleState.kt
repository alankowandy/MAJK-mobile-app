package org.example.majk.majk.presentation.majk_main.majk_my_schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.example.majk.majk.domain.MedicineEntry

data class ScheduleState(
    val selectedDate: LocalDate,
    val currentDateTime: LocalDateTime,
    val schedule: Map<Int, List<MedicineEntry>>
)
