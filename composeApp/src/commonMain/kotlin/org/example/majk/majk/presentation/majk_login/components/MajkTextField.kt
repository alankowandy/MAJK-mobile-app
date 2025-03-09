package org.example.majk.majk.presentation.majk_login.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite

@Composable
fun MajkTextField(
    value: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onTextChange,
        shape = RoundedCornerShape(100),
        placeholder = {
            Text(
                text = placeholder
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = OffWhite,
            focusedContainerColor = OffWhite,
            focusedTextColor = DarkTeal
        )
    )
}