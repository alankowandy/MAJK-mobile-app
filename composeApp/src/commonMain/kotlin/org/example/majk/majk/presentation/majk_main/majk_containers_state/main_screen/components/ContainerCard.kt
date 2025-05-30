package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.container_state
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.WarningRed
import org.example.majk.core.presentation.WatchYellow
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContainerCard(
    containerNumber: Long,
    medicamentName: String,
    containerState: String,
    numberOfPills: Long,
    onSettingsClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = OffWhite),
        border = BorderStroke(
            width = 1.dp,
            color = DarkTeal
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pojemnik $containerNumber",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = DarkTeal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { onSettingsClick() },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = DarkTeal
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "settings"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = DarkTeal, shape = RoundedCornerShape(16.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Medication,
                    contentDescription = null,
                    tint = OffWhite,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )

                Text(
                    text = medicamentName.ifBlank { "Brak leku" },
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = OffWhite,
                    modifier = Modifier
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .background(
                    color = LightGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = DarkTeal,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.container_state),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkTeal,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )

                Text(
                    text = containerState,
                    fontSize = 14.sp,
                    color = OffWhite,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(6.dp)
                        .background(
                            color = when (containerState) {
                                "dużo" -> GoGreen
                                "mało" -> WatchYellow
                                else -> WarningRed
                            },
                            shape = RoundedCornerShape(9.dp)
                        )
                        .padding(horizontal = 30.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .background(
                        color = OffWhite,
                        shape = RoundedCornerShape(100)
                    )
                    .border(
                        width = 2.dp,
                        color = DarkTeal,
                        shape = RoundedCornerShape(100)
                    )
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            val pillAmount = (numberOfPills.toString().padStart(2, ' '))
                            append(pillAmount)
                        }
                        append("\n")
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp
                            )
                        ) {
                            append("szt.")
                        }
                    },
                    fontSize = 10.sp,
                    color = DarkTeal
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

    }
}