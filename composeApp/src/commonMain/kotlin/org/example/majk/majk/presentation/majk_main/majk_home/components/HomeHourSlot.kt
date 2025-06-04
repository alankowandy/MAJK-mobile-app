package org.example.majk.majk.presentation.majk_main.majk_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.daysUntil
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.majk.domain.ReleaseSchedule
import org.example.majk.majk.presentation.majk_main.majk_home.HomeState

@Composable
fun HomeHourSlot(
    schedules: List<ReleaseSchedule>,
    state: HomeState,
    hour: Int,
    nextHourForToday: Int?
) {
    if (hour != nextHourForToday) {
        return
    }

    // Otherwise, we know exactly which schedule is “next” at this hour.
    val currentDayTime = state.currentDay
    val scheduleToShow = schedules.firstNotNullOfOrNull { schedule ->
        val sd = LocalDateTime.parse(schedule.startDate)
        val ed = LocalDateTime.parse(schedule.endDate)
        val date = currentDayTime.date
        if (date < sd.date || date > ed.date) return@firstNotNullOfOrNull null
        val daysBetween = sd.date.daysUntil(date)
        if (daysBetween % schedule.repeatingInterval != 0L) return@firstNotNullOfOrNull null
        if (sd.time.hour == hour) return@firstNotNullOfOrNull schedule
        null
    }

    // If for some reason there is no actual matching schedule, render nothing
    if (scheduleToShow == null) {
        Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
        return
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text  = hour.toString().padStart(2, '0') + ":00",
            color = DarkTeal,
            modifier = Modifier
        )

        Column(
            modifier = Modifier
                .padding(start = 64.dp)
                .fillMaxWidth()
        ) {
            HomeMedicineCard(
                schedule = scheduleToShow,
                state = state,
                selectedDate = state.currentDay.date
            )
        }
    }
}