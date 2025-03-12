package org.example.majk.majk.presentation.majk_login.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.core.presentation.OffWhite

@Composable
fun MajkTextField(
    value: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean,
    keyboardType: KeyboardType
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = DarkTeal,
            backgroundColor = DarkTeal
        )
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onTextChange,
            shape = RoundedCornerShape(100),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        //fontWeight = FontWeight.Bold,
                        color = DarkTeal
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = LightGray,
                focusedContainerColor = LightGray,
                focusedTextColor = DarkTeal,
                unfocusedTextColor = DarkTeal,
                focusedBorderColor = DarkTeal
            ),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .size(width = 310.dp, height = 60.dp)
        )
    }
}