package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite

@Composable
fun AuthCheckbox(
    isChecked: Boolean,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = DarkTeal,
                uncheckedColor = DarkTeal,
                checkmarkColor = OffWhite
            )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            color = DarkTeal,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}