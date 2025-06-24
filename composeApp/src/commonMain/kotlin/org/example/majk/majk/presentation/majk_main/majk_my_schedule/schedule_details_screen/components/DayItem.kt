package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import org.example.majk.core.presentation.theme.Cyan
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.LightGray

@Composable
fun DayItem(
    date: LocalDate,
    selected: Boolean,
    onClick: () -> Unit
) {

    val polishWeekdays = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "Sb", "N")
    val polishMonths   = listOf(
        "Sty","Lut","Mar","Kwi","Maj","Cze",
        "Lip","Sie","Wrz","Paź","Lis","Gru"
    )

    val dowIndex = date.dayOfWeek.isoDayNumber - 1
    val dayLabel    = polishWeekdays.getOrNull(dowIndex) ?: date.dayOfWeek.name.take(2)
    val dayNumber   = date.dayOfMonth.toString()
    val monthLabel  = polishMonths.getOrNull(date.monthNumber - 1) ?: date.monthNumber.toString()

    val backgroundColor = if (selected) Cyan else LightGray
    val borderColor = if (selected) Color.White else DarkTeal
    val elevation = if (selected) 16.dp else 0.dp
    val textColor = Color.White

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 55.dp, height = 80.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = DarkTeal, shape = RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayLabel,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = dayNumber,
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = monthLabel,
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}