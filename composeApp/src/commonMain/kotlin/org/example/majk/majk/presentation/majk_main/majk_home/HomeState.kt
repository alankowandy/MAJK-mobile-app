package org.example.majk.majk.presentation.majk_main.majk_home

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.majk.domain.ContainerState
import org.example.majk.majk.domain.ReleaseSchedule

data class HomeState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val containerList: List<ContainerState> = emptyList(),
    val releaseSchedule: List<ReleaseSchedule> = emptyList(),
    val currentDay: LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw")),
    val nextDoseHourForToday: Int? = null
)