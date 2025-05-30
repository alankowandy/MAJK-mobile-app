package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.back
import majk.composeapp.generated.resources.delete
import majk.composeapp.generated.resources.save
import majk.composeapp.generated.resources.user
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.WarningRed
import org.example.majk.majk.domain.UserSettings
import org.example.majk.majk.presentation.components.MajkButton
import org.example.majk.majk.presentation.components.MajkTextField
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsAction
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsState
import org.jetbrains.compose.resources.stringResource

@Composable
fun ManageFamilySettingsLayout(
    state: SettingsState,
    userSettings: UserSettings,
    onAction: (SettingsAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val usernameFocusRequester = remember { FocusRequester() }

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
            text = "Ustawienia użytkownika\n${userSettings.currentUsername}",
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
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Kolor ikonki",
                    color = DarkTeal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = DarkTeal,
                                shape = RoundedCornerShape(100)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "user",
                            modifier = Modifier
                                .size(90.dp),
                            tint = Color(state.userAvatarColor)
                        )
                    }

                    Spacer(modifier = Modifier.width(36.dp))

                    ColorPicker(
                        onColorClick = { onAction(SettingsAction.OnColorChange(it)) }
                    )
                }

                Text(
                    text = "Nazwa użytkownika",
                    color = DarkTeal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                MajkTextField(
                    value = state.usernameEntry,
                    onTextChange = { onAction(SettingsAction.OnUsernameChange(it)) },
                    placeholder = stringResource(Res.string.user),
                    isPassword = false,
                    keyboardType = KeyboardType.Text,
                    focusRequester = usernameFocusRequester,
                    onNextFocus = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    },
                    isError = state.usernameError,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = "Poziom dostępu",
                    color = DarkTeal,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                MajkSettingsDropdown(
                    value = state.permissionEntry,
                    onAction = onAction
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = stringResource(Res.string.save),
            onAction = { onAction(SettingsAction.OnConfirmClick) },
            boldText = true,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp)
                .fillMaxWidth()
        )

        MajkButton(
            text = stringResource(Res.string.back),
            onAction = { onAction(SettingsAction.OnBackClick) },
            boldText = true,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp)
                .fillMaxWidth()
        )

        MajkButton(
            text = stringResource(Res.string.delete),
            onAction = { onAction(SettingsAction.OnDeleteClick) },
            boldText = true,
            containerColor = WarningRed,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp)
                .fillMaxWidth()
        )
    }
}