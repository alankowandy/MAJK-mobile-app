package org.example.majk.majk.presentation.majk_main.majk_history.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toInstant
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.WarningRed
import org.example.majk.core.presentation.WatchYellow
import org.example.majk.majk.domain.ReleaseHistory
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryState
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@Composable
fun HistoryTile(
    state: HistoryState,
    entry: ReleaseHistory
) {
    val startDateTime = LocalDateTime.parse(entry.startDate)
    val takingDateTime = LocalDateTime.parse(entry.releaseHistoryDate)

    val scheduledAt = LocalDateTime(
        takingDateTime.date,
        startDateTime.time
    )

    val tz = TimeZone.currentSystemDefault()

    val scheduledInstant = scheduledAt.toInstant(tz)
    val takingInstant = takingDateTime.toInstant(tz)

    val diff: Duration = takingInstant - scheduledInstant

    val (statusColor, statusText) = when {
        diff <= 1.hours -> GoGreen to "Przyjęto według harmonogramu"
        diff <= 6.hours -> WatchYellow to "Przyjęto w innym terminie"
        else -> WarningRed to "Dawka pominięta"
    }

    val takingTime = takingDateTime.time
    val takingDate = takingDateTime.date

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = OffWhite),
        border = BorderStroke(
            width = 1.dp,
            color = DarkTeal
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DarkTeal)
                .height(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$takingDate $takingTime",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = OffWhite
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(statusColor)
                .height(25.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = statusText,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = OffWhite
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(LightGray)
                .border(1.dp, DarkTeal, RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OffWhite)
                        .border(1.dp, DarkTeal, RoundedCornerShape(16.dp))
                        .height(40.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = entry.medicamentName,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkTeal
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(OffWhite)
                        .border(1.dp, DarkTeal, RoundedCornerShape(16.dp))
                        .size(70.dp, 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${entry.pillAmount} szt.",
                        fontWeight = FontWeight.SemiBold,
                        color = DarkTeal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}