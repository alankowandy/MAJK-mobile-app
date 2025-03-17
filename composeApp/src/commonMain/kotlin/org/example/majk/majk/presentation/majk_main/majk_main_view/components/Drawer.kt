package org.example.majk.majk.presentation.majk_main.majk_main_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.sign_up
import org.example.majk.app.Route
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.data.itemsInDrawer
import org.example.majk.majk.presentation.majk_login.components.MajkButton
import org.jetbrains.compose.resources.stringResource

@Composable
fun Drawer(
    currentRoute: Route,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(color = OffWhite)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Cześć, Użytkownik 1!",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = DarkTeal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )
        itemsInDrawer.forEach { item ->
            DrawerItem(
                item = item,
                selected = currentRoute.title == item.title,
                modifier = Modifier.padding(horizontal = 40.dp),
                onDrawerItemClick = { onClick() }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        MajkButton(
            text = stringResource(Res.string.sign_up),
            onAction = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )
    }
}