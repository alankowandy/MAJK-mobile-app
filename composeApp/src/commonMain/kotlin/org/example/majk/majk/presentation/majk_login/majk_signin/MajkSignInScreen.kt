package org.example.majk.majk.presentation.majk_login.majk_signin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.majk.presentation.majk_login.components.MajkLogo
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MajkSignInScreenRoot(
    viewModel: MajkSignInViewModel = koinViewModel(),

) {
    val state by viewModel.state.collectAsStateWithLifecycle()
}

@Composable
fun MajkSignInScreen() {

    Column {
        Text(
            text = "Zaloguj się"
        )

        MajkLogo(
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
        )

        Text(
            text = "Podaj login i hasło, aby się zalogować"
        )


    }
}