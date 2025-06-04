package org.example.majk.majk.presentation.majk_main.majk_home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.daysUntil
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.majk.domain.ReleaseSchedule
import org.example.majk.majk.presentation.majk_main.majk_home.HomeState
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components.MedicineCard

@Composable
fun NextDoseBox(
    state: HomeState,
    releaseSchedule: List<ReleaseSchedule>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
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
            text = "Najbliższa dawka:",
            color = DarkTeal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val nextHourForToday = nextDoseHourForToday(releaseSchedule, state.currentDay)

            if (nextHourForToday == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(LightGray)       // or whatever background you want
                        .border(1.dp, DarkTeal, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Brak nadchodzących dawek.",
                        color = DarkTeal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }

            for (hour in 0 until 24) {
                HomeHourSlot(
                    schedules = releaseSchedule,
                    state = state,
                    hour = hour,
                    nextHourForToday = nextHourForToday,
                )
            }
        }
    }
}

private fun nextDoseHourForToday(
    schedules: List<ReleaseSchedule>,
    currentDay: LocalDateTime
): Int? {
    // 1) Filter only schedules that “apply today”:
    val today = currentDay.date
    val candidateHours = schedules.mapNotNull { schedule ->
        // parse this schedule’s start and end as LocalDateTime
        val startDT = LocalDateTime.parse(schedule.startDate)
        val endDT   = LocalDateTime.parse(schedule.endDate)

        // if today is before startDate or after endDate → skip
        if (today < startDT.date || today > endDT.date) return@mapNotNull null

        // check repeating interval
        val daysBetween = startDT.date.daysUntil(today)
        if (daysBetween % schedule.repeatingInterval != 0L) return@mapNotNull null

        // this schedule “fires” today, so return its hour‐of‐day
        startDT.time.hour
    }
    // 2) Out of all those hours, keep only the ones ≥ currentHour
    val futureHours = candidateHours.filter { hourOfDay ->
        hourOfDay > currentDay.time.hour
    }
    // 3) Return the minimum, or null if empty
    return futureHours.minOrNull()
}