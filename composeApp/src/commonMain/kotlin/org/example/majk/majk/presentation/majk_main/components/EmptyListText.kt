package org.example.majk.majk.presentation.majk_main.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.empty_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyListText(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(Res.string.empty_list),
        color = Color.Black.copy(alpha = 0.66f),
        modifier = modifier
    )
}