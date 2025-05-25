package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.components

import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.example.majk.core.presentation.DarkTeal

@Composable
fun EditMedicamentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = DarkTeal
            )
        },
        readOnly = true
    )
}