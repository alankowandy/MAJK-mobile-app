package org.example.majk.majk.presentation.majk_login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajkAlertDialog(
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
                .wrapContentHeight()
                .wrapContentWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            color = LightGray
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    text = error,
                    color = DarkTeal
                )
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = { dismissAction() },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(text = "Ok")
                }
            }
        }
    }
}