package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.after_meal
import majk.composeapp.generated.resources.before_meal
import majk.composeapp.generated.resources.during_meal
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.WarningRed
import org.example.majk.core.presentation.WatchYellow
import org.example.majk.majk.domain.ReleaseSchedule
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@Composable
fun MedicineCard(
    schedule: ReleaseSchedule,
    selectedDate: LocalDate
) {
    val (mealEmoji, mealLabel) = when (schedule.mealDependability) {
        "before" -> "🍽️" to stringResource(Res.string.before_meal)
        "during" -> "🥣" to stringResource(Res.string.during_meal)
        "after"  -> "☕" to stringResource(Res.string.after_meal)
        else     -> ""   to ""
    }

    val statusColor = checkStatus(schedule, selectedDate)

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = OffWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "💊",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(end = 8.dp)
            )

            Text(
                text = "${schedule.medicamentName} x${schedule.pillAmount}",
                style = MaterialTheme.typography.bodyMedium,
                color = DarkTeal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
            )

            if (mealEmoji.isNotBlank()) {
                Text(
                    text = mealEmoji,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
            }
            Text(
                text = mealLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkTeal,
                modifier = Modifier
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                .size(width = 4.dp, height = 24.dp)
                .background(statusColor)
            )
        }
    }
}

private fun checkStatus(
    schedule: ReleaseSchedule,
    selectedDate: LocalDate
): Color {
    val startDateTime = LocalDateTime.parse(schedule.startDate)
    val takingDateTime = schedule.releaseDateTime?.let { LocalDateTime.parse(it) }
    val takingDate = takingDateTime?.date

    if (takingDate == selectedDate) {
        val scheduledAt = LocalDateTime(
            takingDateTime.date,
            startDateTime.time
        )

        val tz = TimeZone.currentSystemDefault()

        val scheduledInstant = scheduledAt.toInstant(tz)
        val takingInstant = takingDateTime.toInstant(tz)

        val diff: Duration = takingInstant.minus(scheduledInstant)

        return when {
            diff <= 1.hours -> GoGreen
            diff <= 6.hours -> WatchYellow
            else -> WarningRed
        }
    } else return WarningRed
}