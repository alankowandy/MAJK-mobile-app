package org.example.majk.majk.presentation.majk_main.majk_manage_family.components

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
    onPermissionsClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
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
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = DarkTeal,
                modifier = Modifier
                    .padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {

                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "schedule",
                    tint = DarkTeal
                )
            }

            IconButton(
                onClick = {

                },
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "settings",
                    tint = DarkTeal
                )
            }
        }
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//        ) {
//            Text(
//                text = username,
//                fontWeight = FontWeight.Bold,
//                fontSize = 24.sp,
//                textAlign = TextAlign.Center,
//                color = DarkTeal,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 16.dp)
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 12.dp)
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Box(
//                        contentAlignment = Alignment.Center,
//                        modifier = Modifier
//                            .padding(top = 40.dp)
//                            .size(120.dp)
//                            .background(
//                                color = DarkTeal,
//                                shape = RoundedCornerShape(100)
//                            )
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.Person,
//                            contentDescription = "user",
//                            modifier = Modifier
//                                .size(100.dp),
//                            tint = userColor
//                        )
//                    }
//                }
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .padding(start = 20.dp, end = 8.dp)
//                ) {
//                    MajkButton(
//                        text = "harmonogram",
//                        onAction = { onScheduleClick() },
//                        boldText = false,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
//
//                    MajkButton(
//                        text = "uprawnienia",
//                        onAction = { onPermissionsClick() },
//                        boldText = false,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
//
//                    MajkButton(
//                        text = "edytuj",
//                        onAction = { onEditClick() },
//                        boldText = false,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
//
//                    MajkButton(
//                        text = "usuń",
//                        onAction = { onDeleteClick() },
//                        boldText = false,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
//                }
//            }
//        }
    }
}