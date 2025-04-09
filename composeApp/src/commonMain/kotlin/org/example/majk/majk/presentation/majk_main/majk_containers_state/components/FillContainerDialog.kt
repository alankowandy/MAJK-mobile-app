package org.example.majk.majk.presentation.majk_main.majk_containers_state.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillContainerDialog(
    containerNumber: Int,
    dismissAction: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { dismissAction() },
    ) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            shape = MaterialTheme.shapes.large,
            color = LightGray,
            border = BorderStroke(
                width = 1.dp,
                color = DarkTeal
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    text = "Uzupełnij pojemnik",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                Text(
                    text = "Dokonujesz zmiany dla Pojemnika $containerNumber",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .background(
                            color = DarkTeal,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                )

                Spacer(Modifier.weight(1f))

                TextButton(
                    onClick = { dismissAction() },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}