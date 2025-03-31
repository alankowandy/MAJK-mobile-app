package org.example.majk.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.majk.core.data.DrawerData
import org.example.majk.core.presentation.Cyan
import org.example.majk.core.presentation.DarkTeal

@Composable
fun DrawerItem(
    item: DrawerData,
    selected: Boolean,
    modifier: Modifier,
    onDrawerItemClick: () -> Unit
) {
    val itemSelected: FontWeight = if (selected) FontWeight.Bold else FontWeight.Normal

    Button(
        onClick = { onDrawerItemClick() },
        shape = RoundedCornerShape(24.dp),
        colors = ButtonColors(
            containerColor = Cyan,
            contentColor = DarkTeal,
            disabledContainerColor = Cyan,
            disabledContentColor = DarkTeal
        ),
        border = BorderStroke(
            width = 1.dp,
            color = DarkTeal
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = item.title,
                fontWeight = itemSelected,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}