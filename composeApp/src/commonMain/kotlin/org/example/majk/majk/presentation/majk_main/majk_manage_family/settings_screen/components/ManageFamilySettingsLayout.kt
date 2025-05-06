package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.back
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
    onBackClick: () -> Unit,
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
            text = "Nazwa użytkownika",
            color = DarkTeal,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 24.dp)
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
            isError = state.usernameError
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Poziom dostępu",
            color = DarkTeal,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        MajkSettingsDropdown(
            value = state.permissionEntry,
            onAction = onAction
        )

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = "Zapisz",
            onAction = { onAction(SettingsAction.OnConfirmClick) },
            boldText = true,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(45.dp)
        )

        MajkButton(
            text = stringResource(Res.string.back),
            onAction = { onAction(SettingsAction.OnBackClick) },
            boldText = true,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(45.dp)
        )

        MajkButton(
            text = "Usuń",
            onAction = {
                onAction(SettingsAction.OnDeleteClick)
                if (state.errorMessage == null) {
                    onBackClick()
                }
            },
            boldText = true,
            containerColor = WarningRed,
            modifier = Modifier
                .padding(start = 70.dp, end = 70.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(45.dp)
        )
    }
}