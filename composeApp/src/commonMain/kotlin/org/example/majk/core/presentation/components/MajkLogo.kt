package org.example.majk.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.majk_icon_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun MajkLogo(
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp
    ) {
        Image(
            painterResource(Res.drawable.majk_icon_white),
            contentDescription = "Majk logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}