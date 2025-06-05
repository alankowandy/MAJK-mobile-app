package org.example.majk.majk.presentation.majk_login.majk_signup

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import majk.composeapp.generated.resources.back
import majk.composeapp.generated.resources.email
import majk.composeapp.generated.resources.family_code
import majk.composeapp.generated.resources.password
import majk.composeapp.generated.resources.sign_up
import majk.composeapp.generated.resources.user
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.components.MajkAlertDialog
import org.example.majk.core.presentation.components.MajkButton
import org.example.majk.core.presentation.components.MajkLogo
import org.example.majk.core.presentation.components.MajkTextField
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MajkSignUpScreenRoot(
    viewModel: MajkSignUpViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MajkSignUpScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is MajkSignUpAction.OnBackClick -> onBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun MajkSignUpScreen(
    state: MajkSignUpState,
    onAction: (MajkSignUpAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val usernameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val familyCodeFocusRequester = remember { FocusRequester() }

    if (state.errorMessage != null) {
        MajkAlertDialog(
            error = state.errorMessage,
            dismissAction = {
                onAction(MajkSignUpAction.OnErrorClear)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

//        Text(
//            text = stringResource(Res.string.sign_up),
//            style = TextStyle(
//                fontSize = 18.sp,
//                color = DarkTeal,
//                fontWeight = FontWeight.Bold
//            ),
//            textAlign = TextAlign.Center,
//            lineHeight = 24.sp,
//            modifier = Modifier
//                .padding(horizontal = 50.dp)
//        )
//
//        Spacer(modifier = Modifier.weight(1f))

        MajkLogo(
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Podaj nazwę użytkownika, e-mail, hasło i kod rodziny",
            style = TextStyle(
                fontSize = 16.sp,
                color = DarkTeal,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier
                .padding(horizontal = 50.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        MajkTextField(
            value = state.usernameEntry,
            onTextChange = { onAction(MajkSignUpAction.OnUsernameChange(it)) },
            placeholder = stringResource(Res.string.user),
            isPassword = false,
            keyboardType = KeyboardType.Text,
            focusRequester = usernameFocusRequester,
            onNextFocus = { emailFocusRequester.requestFocus() },
            isError = state.usernameError
        )

        MajkTextField(
            value = state.emailEntry,
            onTextChange = { onAction(MajkSignUpAction.OnEmailChange(it)) },
            placeholder = stringResource(Res.string.email),
            isPassword = false,
            keyboardType = KeyboardType.Email,
            focusRequester = emailFocusRequester,
            onNextFocus = { passwordFocusRequester.requestFocus() },
            isError = state.emailError
        )

        MajkTextField(
            value = state.passwordEntry,
            onTextChange = { onAction(MajkSignUpAction.OnPasswordChange(it)) },
            placeholder = stringResource(Res.string.password),
            isPassword = true,
            keyboardType = KeyboardType.Password,
            focusRequester = passwordFocusRequester,
            onNextFocus = { familyCodeFocusRequester.requestFocus() },
            isError = state.passwordError
        )

        MajkTextField(
            value = state.familyCode,
            onTextChange = { onAction(MajkSignUpAction.OnFamilyCodeChange(it)) },
            placeholder = stringResource(Res.string.family_code),
            isPassword = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            focusRequester = familyCodeFocusRequester,
            onNextFocus = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
            isError = state.familyCodeError
        )

        Spacer(modifier = Modifier.weight(1f))

        MajkButton(
            text = stringResource(Res.string.sign_up),
            onAction = { onAction(MajkSignUpAction.OnSignUpClick) },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )

        MajkButton(
            text = stringResource(Res.string.back),
            onAction = { onAction(MajkSignUpAction.OnBackClick) },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp, bottom = 20.dp)
        )
    }
}