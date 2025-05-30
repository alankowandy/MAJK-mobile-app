package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    onColorClick: (Int) -> Unit
) {
    val colors = listOf(
        Color.White, Color(0xFFFFD700), Color(0xFFB22222), Color(0xFF3CB371), Color(0xFF9932CC),
        Color(0xFF98FF98), Color(0xFFFFB6C1), Color(0xFFFF8C00), Color(0xFF40E0D0), Color(0xFF0000FF),
        Color(0xFFFFE4B5), Color(0xFF8B4513), Color(0xFFADFF2F), Color(0xFF808080), Color(0xFF000000)
    )

    Column {
        colors.chunked(5).forEach { rowColors ->
            Row {
                rowColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color)
                            .clickable { onColorClick(color.toArgb()) }
                    )
                }
            }
        }
    }
}