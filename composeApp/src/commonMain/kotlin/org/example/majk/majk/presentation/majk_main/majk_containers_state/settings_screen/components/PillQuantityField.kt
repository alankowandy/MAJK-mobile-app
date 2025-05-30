package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsAction
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsState

@Composable
fun PillQuantityField(
    state: ContainerSettingsState,
    onAction: (ContainerSettingsAction) -> Unit,
    focusRequester: FocusRequester,
    onNextFocus: () -> Unit,
    isError: Boolean
) {

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = DarkTeal,
            backgroundColor = DarkTeal.copy(alpha = 0.33F)
        )
    ) {
        TextField(
            value = state.pillQuantityEntry,
            onValueChange = { onAction(ContainerSettingsAction.OnPillQuantityChange(it)) },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            trailingIcon = {
                if (state.pillQuantityEntry.isNotBlank()) {
                    IconButton(
                        onClick = {
                            onAction(ContainerSettingsAction.OnEmptyContainerClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "empty"
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onNextFocus()
                }
            ),
            textStyle = TextStyle(
                fontSize = 18.sp,
                lineHeight = 14.sp
            ),
            isError = isError,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightGray,
                focusedContainerColor = LightGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = DarkTeal
            ),
            modifier = Modifier
                .focusRequester(focusRequester)
                .size(width = 120.dp, height = 60.dp)
        )
    }
}