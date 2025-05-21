package org.example.majk.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.majk.core.domain.FamilyUsers
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite

@Composable
fun ActionDropdown(
    familyCode: String,
    deviceCode: String,
    familyUsers: List<FamilyUsers>,
    isAdmin: Boolean,
    onUserClick: () -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(16.dp),
        containerColor = OffWhite,
        shadowElevation = 6.dp,
        border = BorderStroke(color = DarkTeal, width = 1.dp),
        modifier = Modifier
            .width(200.dp)
    ) {
        DropdownMenuItem(
            onClick = { /* no-op */ },
            text = {
                Column {
                    Text(
                        text = "Kod rodziny:",
                        fontWeight = FontWeight.Bold,
                        color = DarkTeal
                    )
                    Text(
                        text = familyCode,
                        color = DarkTeal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = LightGray, shape = RoundedCornerShape(6.dp))
                    )
                }
            }
        )
        // device code
        DropdownMenuItem(
            onClick = { /* no-op */ },
            text = {
                Column {
                    Text(
                        text = "Kod urządzenia:",
                        fontWeight = FontWeight.Bold,
                        color = DarkTeal
                    )
                    Text(
                        text = deviceCode,
                        color = DarkTeal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = LightGray, shape = RoundedCornerShape(6.dp))
                    )
                }
            }
        )

        if (isAdmin) {
            HorizontalDivider()
            familyUsers.forEach { user ->
                DropdownMenuItem(
                    onClick = {
                        onUserClick()
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Cyan, shape = RoundedCornerShape(6.dp))
                        ) {
                            Text(
                                text = user.username,
                                fontWeight = FontWeight.Bold,
                                color = DarkTeal
                            )
                            Text(
                                text = user.permission,
                                color = DarkTeal,
                                fontSize = 6.sp
                            )
                        }
                    }
                )
            }
        }
    }
//    Dialog(
//        onDismissRequest = { onDismiss() },
//        properties = DialogProperties(
//            usePlatformDefaultWidth = false
//        )
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Column(
//                modifier = Modifier
//                    .wrapContentSize(Alignment.TopEnd)
//                    .offset(x = (-16).dp, y = 56.dp)
//                    .padding(8.dp)
//            ) {
//                Card(
//                    shape = RoundedCornerShape(12.dp),
//                    colors = CardDefaults.cardColors(
//                        containerColor = OffWhite
//                    ),
//                    modifier = Modifier
//                        .width(200.dp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .padding(12.dp)
//                    ) {
//                        Text(
//                            text = "Kod rodziny:",
//                            color = DarkTeal
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = familyCode,
//                            color = DarkTeal,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(
//                                    color = LightGray,
//                                    shape = RoundedCornerShape(8.dp)
//                                )
//                                .padding(6.dp)
//                        )
//
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        Text(
//                            text = "Kod urządzenia:",
//                            color = DarkTeal
//                        )
//                        Spacer(modifier = Modifier.height(4.dp))
//                        Text(
//                            text = deviceCode,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .background(
//                                    color = LightGray,
//                                    shape = RoundedCornerShape(8.dp)
//                                )
//                                .padding(6.dp)
//                        )
//
//                        if (isAdmin) {
//                            Spacer(modifier = Modifier.height(12.dp))
//                            familyUsers.forEach { user ->
//                                TextButton(
//                                    onClick = onUserClick,
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .padding(vertical = 8.dp),
//                                    shape = RoundedCornerShape(12.dp),
//                                    colors = ButtonDefaults.buttonColors(
//                                        containerColor = Cyan
//                                    ),
//                                    border = BorderStroke(width = 1.dp, color = DarkTeal)
//                                ) {
//                                    Text(
//                                        text = user.username,
//                                        color = DarkTeal,
//                                        fontWeight = FontWeight.Bold
//                                    )
//                                    Text(
//                                        text = user.permission,
//                                        color = DarkTeal,
//                                        fontSize = 6.sp
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}