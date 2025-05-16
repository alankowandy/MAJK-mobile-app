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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.majk.domain.MedicineEntry

@Composable
fun MedicineCard(medicine: MedicineEntry) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
            // Pill icon (for example purposes, using emoji or a simple shape)
            Text(text = "💊", fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
            // Medicine name
            Text(text = medicine.name, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
            // Status indicator (vertical line) on right: green if taken, red if not taken
            Box(modifier = Modifier
                .size(width = 4.dp, height = 24.dp)
                .background(if (medicine.taken) Color(0xFF4CAF50) else Color(0xFFE91E63))
            )
        }
    }
}