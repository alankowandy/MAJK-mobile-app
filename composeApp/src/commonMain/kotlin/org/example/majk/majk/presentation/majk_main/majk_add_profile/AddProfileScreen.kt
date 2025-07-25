package org.example.majk.majk.presentation.majk_main.majk_add_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.user
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.core.presentation.theme.WarningRed
import org.example.majk.core.presentation.components.MajkButton
import org.example.majk.core.presentation.components.MajkLogo
import org.example.majk.core.presentation.components.MajkTextField
import org.example.majk.core.presentation.components.MajkAlertDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddProfileScreenRoot(
    viewModel: AddProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AddProfileScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AddProfileAction.OnUsernameChange -> viewModel.onAction(action)
                is AddProfileAction.OnAddProfileClick -> viewModel.onAction(action)
                is AddProfileAction.OnDialogClear -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun AddProfileScreen(
    state: AddProfileState,
    onAction: (AddProfileAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val usernameFocusRequester = remember { FocusRequester() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite)
            .padding(top = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            }
    ) {
        if (state.errorMessage != null) {
            MajkAlertDialog(
                error = state.errorMessage,
                dismissAction = {
                    onAction(AddProfileAction.OnDialogClear)
                }
            )
        }

        if (state.successMessage != null) {
            MajkAlertDialog(
                title = "Sukces!",
                error = state.successMessage,
                dismissAction = {
                    onAction(AddProfileAction.OnDialogClear)
                }
            )
        }

        MajkLogo(
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Podaj nazwę użytkownika",
            style = TextStyle(
                fontSize = 24.sp,
                color = DarkTeal,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier
                .padding(horizontal = 50.dp, vertical = 10.dp)
        )

        MajkTextField(
            value = state.usernameEntry,
            onTextChange = { onAction(AddProfileAction.OnUsernameChange(it)) },
            placeholder = stringResource(Res.string.user),
            isPassword = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            focusRequester = usernameFocusRequester,
            onNextFocus = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            isError = state.usernameError
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 10.dp)
                .background(
                    color = WarningRed,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                text = "Dostęp do profilu ma wyłącznie Administrator!",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = OffWhite
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            )
        }

        MajkButton(
            text = "Dodaj profil",
            onAction = { onAction(AddProfileAction.OnAddProfileClick) },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}