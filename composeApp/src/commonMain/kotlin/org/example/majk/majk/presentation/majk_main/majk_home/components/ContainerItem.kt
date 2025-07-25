package org.example.majk.majk.presentation.majk_main.majk_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.GoGreen
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.core.presentation.theme.WarningRed
import org.example.majk.core.presentation.theme.WatchYellow
import org.example.majk.majk.domain.ContainerState

@Composable
fun ContainerItem(
    container: ContainerState,
    onClick: (Long) -> Unit
) {
    val (statusColor, labelText) = when (container.containerState) {
        "pusty" -> WarningRed to "pusty"
        "mało" -> WatchYellow to "mało"
        else -> GoGreen to "dużo"
    }

    Column(
        modifier = Modifier
            .border(1.dp, DarkTeal, RoundedCornerShape(12.dp))
            .background(OffWhite, RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable { onClick(container.containerId) }
    ) {
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                append("Pojemnik\n")
                append(container.containerNumber.toString())
            },
            color = DarkTeal,
            fontSize = 14.sp
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(statusColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = labelText,
                color = OffWhite,
                fontSize = 12.sp
            )
        }
    }
}