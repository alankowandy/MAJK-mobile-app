package org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen

import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.majk.domain.MedicineEntry

data class ScheduleState(
    val selectedDate: LocalDate = LocalDate.today(),
    val currentDateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")),
    val monthOffset: Int = 0,
    val currentAccountId: Long = 0L,
)
