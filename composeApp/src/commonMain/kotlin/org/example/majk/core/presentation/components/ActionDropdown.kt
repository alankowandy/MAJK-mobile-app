package org.example.majk.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
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
    val clip = LocalClipboardManager.current

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
            onClick = { clip.setText(AnnotatedString(familyCode)) },
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
                            .padding(start = 8.dp)
                    )
                }
            }
        )
        // device code
        DropdownMenuItem(
            onClick = { clip.setText(AnnotatedString(deviceCode)) },
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
                            .padding(start = 8.dp)
                    )
                }
            }
        )

        if (isAdmin) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
            )
            familyUsers
                .sortedBy { it.userId }
                .forEach { user ->
                DropdownMenuItem(
                    onClick = {
                        onUserClick()
                    },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = user.username,
                                fontWeight = FontWeight.Bold,
                                color = DarkTeal
                            )
                            Text(
                                text = when (user.permission) {
                                    "admin" -> "Administrator"
                                    "user" -> "Użytkownik"
                                    else -> "Ograniczony dostęp"
                                },
                                color = DarkTeal,
                                fontSize = 12.sp
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                        .background(color = Cyan, shape = RoundedCornerShape(16.dp))
                )
            }
        }
    }
}