package org.example.majk.majk.presentation.majk_login.majk_signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.majk_icon_white
import org.example.majk.majk.presentation.majk_login.components.MajkLogo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MajkSignInScreenRoot(
    viewModel: MajkSignInViewModel = koinViewModel(),

) {
    val state by viewModel.state.collectAsStateWithLifecycle()
}

@Composable
fun MajkSignIn() {

    var text by remember { mutableStateOf(String) }

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