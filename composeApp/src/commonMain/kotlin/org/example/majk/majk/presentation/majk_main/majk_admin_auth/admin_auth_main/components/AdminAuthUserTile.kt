package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import org.example.majk.majk.domain.AdminAuthUsers
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.AdminAuthAction
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.AdminAuthState

@Composable
fun AdminAuthUserTile(
    state: AdminAuthState,
    user: AdminAuthUsers,
    userColor: Int,
    onAction: (AdminAuthAction) -> Unit
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
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = user.username,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = DarkTeal
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        color = DarkTeal,
                        shape = RoundedCornerShape(100)
                    )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "user",
                    modifier = Modifier
                        .size(60.dp),
                    tint = Color(userColor)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthCheckbox(
                    isChecked = user.isNfcChecked,
                    label = "NFC",
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .clickable {
                            onAction(AdminAuthAction.OnNfcClick(
                                accountId = user.id,
                                username = user.username
                            ))
                        }
                )

                AuthCheckbox(
                    isChecked = user.isRfidChecked,
                    label = "RFID",
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .clickable {
                            onAction(AdminAuthAction.OnRfidClick(user.id))
                        }
                )
            }

        }
    }
}
