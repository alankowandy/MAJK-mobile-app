package org.example.majk.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajkAlertDialog(
    title: String = "Coś poszło nie tak",
    error: String,
    dismissAction: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            dismissAction()
        }
    ) {
        Surface(
            modifier = Modifier
                .height(250.dp)
                .width(80.dp),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = LightGray,
            border = BorderStroke(
                width = 1.dp,
                color = DarkTeal
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkTeal
                    ),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium.copy(color = DarkTeal),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )

                Spacer(Modifier.weight(1f))

                TextButton(
                    onClick = { dismissAction() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = DarkTeal
                    ),
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(text = "Ok")
                }
            }
        }
    }
}