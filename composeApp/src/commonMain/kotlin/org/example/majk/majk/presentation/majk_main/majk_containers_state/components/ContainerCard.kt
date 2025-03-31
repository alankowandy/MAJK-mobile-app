package org.example.majk.majk.presentation.majk_main.majk_containers_state.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.GoGreen
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.WarningRed
import org.example.majk.core.presentation.WatchYellow
import org.example.majk.majk.presentation.majk_login.components.MajkButton

@Composable
fun ContainerCard(
    containerNumber: Int,
    medicamentName: String,
    state: String,
    numberOfPills: Int,
    onFillContainerClick: () -> Unit,
    onEmptyContainerClick: () -> Unit,
    onReplaceMedicamentClick: () -> Unit,
    onInformationClick: () -> Unit
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
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Pojemnik $containerNumber",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = DarkTeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = medicamentName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = OffWhite,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = DarkTeal,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            color = LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = DarkTeal,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Stan pojemnika",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkTeal,
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )

                    Text(
                        text = state,
                        fontSize = 14.sp,
                        color = OffWhite,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                color = when (state) {
                                    "pełny" -> GoGreen
                                    "mało" -> WatchYellow
                                    else -> WarningRed
                                },
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 2.dp)
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(8.dp)
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
                            text = "$numberOfPills\nszt.",
                            fontSize = 18.sp,
                            color = DarkTeal
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MajkButton(
                        text = "uzupełnij pojemnik",
                        onAction = { onFillContainerClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )

                    MajkButton(
                        text = "opróżnij pojemnik",
                        onAction = { onFillContainerClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )

                    MajkButton(
                        text = "zastąp lekarstwo",
                        onAction = { onFillContainerClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )

                    MajkButton(
                        text = "informacje",
                        onAction = { onFillContainerClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
        }
    }
}