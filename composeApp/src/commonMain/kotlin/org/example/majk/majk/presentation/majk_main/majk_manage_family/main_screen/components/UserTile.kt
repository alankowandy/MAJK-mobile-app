package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.components.MajkButton

@Composable
fun UserTile(
    username: String,
    userColor: Color,
    onScheduleClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray),
        border = BorderStroke(
            width = 1.dp,
            color = DarkTeal
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = DarkTeal,
                        shape = RoundedCornerShape(100)
                    )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "user",
                    modifier = Modifier
                        .size(40.dp),
                    tint = userColor
                )
            }

            Text(
                text = username,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = DarkTeal,
                modifier = Modifier
                    .padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onScheduleClick() },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "schedule",
                    tint = DarkTeal
                )
            }

            IconButton(
                onClick = { onSettingsClick() },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "settings",
                    tint = DarkTeal
                )
            }
        }
    }
}