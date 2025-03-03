package org.example.majk.majk.presentation.majk_start

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.compose_multiplatform
import majk.composeapp.generated.resources.majk_icon_white
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.Greeting
import org.jetbrains.compose.resources.painterResource

@Composable
fun MajkStart(
    navController: NavController
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

        Surface(
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 6.dp
        ) {
            Image(
                painterResource(Res.drawable.majk_icon_white),
                contentDescription = "Majk logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

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
            onClick = { navController.navigateUp() },
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