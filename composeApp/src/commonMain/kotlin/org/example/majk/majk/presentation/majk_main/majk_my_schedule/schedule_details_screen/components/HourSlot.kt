package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.majk.majk.domain.MedicineEntry
import kotlinx.datetime.*
import org.example.majk.majk.domain.ReleaseSchedule
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.ScheduleState
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsState
import org.koin.core.component.getScopeName
import kotlin.time.Duration.Companion.hours

@Composable
fun HourSlot(
    state: DetailsState,
    hour: Int,
    releaseSchedule: List<ReleaseSchedule>,
    showCurrentTimeLine: Boolean
) {

    val hourLabel = "$hour:00"

    val medsThisHour = releaseSchedule.filter { schedule ->
        displayMedicineCalc(
            schedule = schedule,
            selectedDay = state.selectedDate,
            hour = hour
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        if (showCurrentTimeLine) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                val yOffset = size.height * (state.currentMinute / 60f)
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, yOffset),
                    end = Offset(size.width, yOffset),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = hourLabel,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.TopStart)
            )

            if (medsThisHour.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(start = 64.dp)
                        .fillMaxWidth()
                ) {
                    medsThisHour.forEach { med ->
                        MedicineCard(schedule = med)
                    }
                }
            }
        }
    }

    // Divider line below each hour row (optional, to separate hours)
    Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
}

private fun displayMedicineCalc(
    schedule: ReleaseSchedule,
    selectedDay: LocalDate,
    hour: Int
): Boolean {
    return try {
        val startDateTime = LocalDateTime.parse(schedule.startDate)
        val endDateTime = LocalDateTime.parse(schedule.endDate)

        val startDate = startDateTime.date
        val endDate = endDateTime.date
        val doseHour = startDateTime.time.hour

        if (selectedDay < startDate || selectedDay > endDate) return false

        val daysBetween = startDate.daysUntil(selectedDay)
        if (daysBetween % schedule.repeatingInterval != 0L) return false

        return hour == doseHour
    } catch (e: Exception) {
        println(e)
        false
    }
}