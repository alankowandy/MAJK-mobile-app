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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.ReleaseSchedule

@Composable
fun MedicineCard(
    schedule: ReleaseSchedule
) {
    val (mealEmoji, mealLabel) = when (schedule.mealDependability) {
        "before" -> "🍽️" to "Przed posiłkiem"
        "during" -> "🥣" to "Podczas posiłku"
        "after"  -> "☕" to "Po posiłku"
        else     -> ""   to ""
    }

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
            // Medicine name
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
            // Status indicator (vertical line) on right: green if taken, red if not taken
            Box(
                modifier = Modifier
                .size(width = 4.dp, height = 24.dp)
                .background(GoGreen)
            )
        }
    }
}