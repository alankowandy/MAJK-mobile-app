package org.example.majk.majk.presentation.majk_main.majk_home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.daysUntil
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.majk.domain.ReleaseSchedule
import org.example.majk.majk.presentation.majk_main.majk_home.HomeState

@Composable
fun SkippedDoseBox(
    state: HomeState,
    releaseSchedule: List<ReleaseSchedule>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(1.dp, DarkTeal),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Text(
            text = "Pominięte dawki:",
            color = DarkTeal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val skippedDoses = skippedDosesForToday(releaseSchedule, state.currentDay)

            if (skippedDoses.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LightGray.copy(alpha = 0.5f))       // or whatever background you want
                        .border(1.dp, DarkTeal, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Brak pominiętych dawek.",
                        color = DarkTeal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }

            for (hour in 0 until 24) {
                val matchesThisHour = skippedDoses.any { entry ->
                    val startDateTime = LocalDateTime.parse(entry.startDate)
                    startDateTime.time.hour == hour
                }

                if (matchesThisHour) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text  = hour
                                .toString().padStart(2, '0') + ":00",
                            color = DarkTeal,
                            modifier = Modifier
                        )

                        Column(
                            modifier = Modifier
                                .padding(start = 64.dp)
                                .fillMaxWidth()
                        ) {
                            skippedDoses
                                .filter { entry ->
                                    LocalDateTime.parse(entry.startDate).time.hour == hour
                                }
                                .forEach { entry ->
                                    HomeMedicineCard(
                                        state = state,
                                        schedule = entry,
                                        selectedDate = state.currentDay.date
                                    )
                                }
                        }
                    }
                    Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
                }
            }
        }
    }
}

private fun skippedDosesForToday(
    schedules: List<ReleaseSchedule>,
    currentDateTime: LocalDateTime
): List<ReleaseSchedule> {
    val todayDate = currentDateTime.date
    val currentHour = currentDateTime.time.hour

    return schedules
        .mapNotNull { sched ->
            // Parse the schedule’s start/end timestamps into LocalDateTime
            val startDT = LocalDateTime.parse(sched.startDate)
            val endDT   = LocalDateTime.parse(sched.endDate)

            // 1) Is “today” inside [startDate .. endDate]? If not, skip.
            if (todayDate < startDT.date || todayDate > endDT.date) return@mapNotNull null

            // 2) Check repeating interval: only include if (today – start.date) % repeatingInterval == 0
            val daysSinceStart = startDT.date.daysUntil(todayDate)
            if (daysSinceStart % sched.repeatingInterval != 0L) return@mapNotNull null

            // 3) We know “this schedule is due sometime today.” Now check its hour
            val scheduleHour = startDT.time.hour
            // † If scheduleHour < currentHour → this one has been “skipped” (because it should have fired earlier)
            if (scheduleHour < currentHour) {
                sched
            } else {
                null
            }
        }
        // Sort by the schedule’s hour, ascending (so earliest‐skipped appear first)
        .sortedBy { LocalDateTime.parse(it.startDate).time.hour }
}