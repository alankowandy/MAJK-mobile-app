package org.example.majk.majk.presentation.majk_login.majk_start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.register_device
import majk.composeapp.generated.resources.sign_in
import majk.composeapp.generated.resources.sign_up
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.majk_login.components.MajkButton
import org.example.majk.majk.presentation.majk_login.components.MajkLogo
import org.jetbrains.compose.resources.stringResource

@Composable
fun MajkStartScreenRoot(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onRegisterDeviceClick: () -> Unit
) {
    MajkStartScreen(
        onAction = { action ->
            when(action) {
                is MajkStartAction.OnSignInClick -> onSignInClick()
                is MajkStartAction.OnSignUpClick -> onSignUpClick()
                is MajkStartAction.OnRegisterDeviceClick -> onRegisterDeviceClick()
            }
        }
    )
}

@Composable
private fun MajkStartScreen(
    onAction: (MajkStartAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = OffWhite
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        MajkLogo(
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        Text(
            text = "Dzień dobry! Oto MAJK,\nTwój inteligentny\nsystem dystrybucji\nleków",
            style = TextStyle(
                fontSize = 18.sp,
                color = DarkTeal
            ),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        MajkButton(
            text = stringResource(Res.string.sign_in),
            onAction = { onAction(MajkStartAction.OnSignInClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        MajkButton(
            text = stringResource(Res.string.sign_up),
            onAction = { onAction(MajkStartAction.OnSignUpClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )

        MajkButton(
            text = stringResource(Res.string.register_device),
            onAction = { onAction(MajkStartAction.OnRegisterDeviceClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
    }
}