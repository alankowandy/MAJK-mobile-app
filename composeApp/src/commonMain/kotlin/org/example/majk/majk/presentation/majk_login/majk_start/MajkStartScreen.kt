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
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.example.majk.app.Route
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.majk_login.components.MajkLogo

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
fun MajkStartScreen(
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

        Button(
            onClick = { onAction(MajkStartAction.OnSignInClick) },
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            androidx.compose.material.Text(
                text = "Zaloguj"
            )
        }
        Button(
            onClick = { TODO() },

            ) {
            androidx.compose.material.Text(
                text = "Zarejestruj konto"
            )
        }
        Button(
            onClick = { TODO() },

            ) {
            androidx.compose.material.Text(
                text = "Zarejestruj urzadzenie"
            )
        }
    }
}