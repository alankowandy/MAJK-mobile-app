package org.example.majk.majk.presentation.majk_main.majk_home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.majk.core.presentation.theme.Cyan

@Composable
fun DateBox(
    currentDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Warsaw"))
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Cyan
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(
            text = currentDate.date.toString()
                .split("-")
                .let { parts -> "${parts[2]}.${parts[1]}.${parts[0]}" },
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 36.dp, vertical = 12.dp)
        )
    }
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(16.dp))
//            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
//            .shadow(8.dp, RoundedCornerShape(16.dp))
//            .background(Cyan, RoundedCornerShape(16.dp))
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        contentAlignment = Alignment.Center
//    ) {
//    }
}