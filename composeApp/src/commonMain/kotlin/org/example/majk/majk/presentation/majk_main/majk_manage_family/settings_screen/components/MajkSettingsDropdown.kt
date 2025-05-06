package org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.LightGray
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajkSettingsDropdown(
    value: String,
    readOnly: Boolean = true,
    onAction: (SettingsAction) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val permissionOptions = listOf("Administrator", "Użytkownik", "Ograniczony")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 16.dp)
    ) {
        androidx.compose.material3.ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = readOnly,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = DarkTeal,
                    focusedBorderColor = DarkTeal,
                    focusedTextColor = DarkTeal,
                    unfocusedTextColor = DarkTeal
                ),
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                containerColor = LightGray
            ) {
                permissionOptions.forEach { value ->
                    DropdownMenuItem(
                        text = { Text(text = value) },
                        onClick = {
                            onAction(SettingsAction.OnPermissionChange(value))
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}