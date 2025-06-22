package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.components.MajkButton
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings.AdminAuthSettingsAction
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings.AdminAuthSettingsState

@Composable
fun AdminAuthSettingsLayout(
    state: AdminAuthSettingsState,
    onAction: (AdminAuthSettingsAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val codeFocusRequester = remember { FocusRequester() }
    val headerText = if (state.settingsList.isEmpty()) "" else state.settingsList[0].username

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ustawienia użytkownika\nUżytkownik 1",
            color = OffWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(DarkTeal, RoundedCornerShape(16.dp))
                .padding(vertical = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = OffWhite
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // NFC Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkTeal,
                            uncheckedColor = DarkTeal,
                            checkmarkColor = OffWhite
                        )
                    )
                    Text(
                        text = "NFC aktywne",
                        fontSize = 16.sp,
                        color = DarkTeal
                    )
                }

                // RFID Checkbox
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {  },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkTeal,
                            uncheckedColor = DarkTeal,
                            checkmarkColor = OffWhite
                        )
                    )
                    Text(
                        text = "RFID aktywne",
                        fontSize = 16.sp,
                        color = DarkTeal
                    )
                }

                // RFID Code Input (only editable if checkbox is active)
                OutlinedTextField(
                    value = "3543015722",
                    onValueChange = {  },
                    label = { Text("Kod RFID") },
                    enabled = state.isRfidChecked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = "Zapisz",
            onAction = {  },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        )

        MajkButton(
            text = "Wstecz",
            onAction = {  },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        )
    }
}