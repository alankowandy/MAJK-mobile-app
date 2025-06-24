package org.example.majk.core.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite

@Composable
fun MajkButton(
    text: String,
    onAction: () -> Unit,
    boldText: Boolean,
    containerColor: Color = DarkTeal,
    shape: Shape = RoundedCornerShape(16.dp),
    modifier: Modifier
) {
    Button(
        onClick = { onAction() },
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = OffWhite,
            containerColor = containerColor
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = if (boldText) FontWeight.Bold else FontWeight.Normal
        )
    }
}