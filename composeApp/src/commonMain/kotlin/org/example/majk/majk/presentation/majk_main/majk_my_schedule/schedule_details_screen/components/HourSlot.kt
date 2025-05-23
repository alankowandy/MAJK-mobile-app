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
import org.koin.core.component.getScopeName
import kotlin.time.Duration.Companion.hours

@Composable
fun HourSlot(
    hour: Int,
    medicines: List<MedicineEntry>,
    showCurrentTimeLine: Boolean,
    currentMinute: Int
) {
    // Format hour as "8:00", "13:00", etc.
    val hourLabel = hour.let {
        "$it:00"
    }
    //val hourLabel = "%02d:00"
    // Each hour slot is a row with the time on the left and any medicine cards on the right
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .background(Color.LightGray.copy(alpha = 0.3f))
        .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Hour text at the start (left)
        Text(
            text = hourLabel,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.TopStart)
        )
        // If any medicines scheduled this hour, display them as cards
        if (medicines.isNotEmpty()) {
            Column(modifier = Modifier
                .padding(start = 64.dp)  // add left padding so cards don't overlap the hour label
                .fillMaxWidth()
            ) {
                medicines.forEach { med ->
                    MedicineCard(medicine = med)
                }
            }
        }
        // Draw a red line for current time within this hour, if applicable
        if (showCurrentTimeLine) {
            // Position the line relative to currentMinute within this hour slot.
            // We draw a line across the full width at a vertical offset proportional to minutes.
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                val yOffset = size.height * (currentMinute / 60f)
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, yOffset),
                    end = Offset(size.width, yOffset),
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }
    // Divider line below each hour row (optional, to separate hours)
    Divider(color = Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
}