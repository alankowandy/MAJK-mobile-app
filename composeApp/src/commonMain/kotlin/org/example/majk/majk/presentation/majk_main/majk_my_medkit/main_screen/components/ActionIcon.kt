package org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.components

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.example.majk.core.presentation.OffWhite

@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = OffWhite
        )
    }
}