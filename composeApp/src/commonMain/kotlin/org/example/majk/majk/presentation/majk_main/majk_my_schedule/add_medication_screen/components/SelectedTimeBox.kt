package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal

@Composable
fun SelectedTimeBox(
    hour: Int,
    minute: Int,
    onClick: () -> Unit
) {
    val hh = hour.toString().padStart(2, '0')
    val mm = minute.toString().padStart(2, '0')

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = DarkTeal, shape = RoundedCornerShape(8.dp))
            .background(Cyan)
            .clickable { onClick() }
            .size(width = 80.dp, height = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$hh:$mm",
            color = DarkTeal,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}