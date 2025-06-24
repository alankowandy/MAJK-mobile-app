package org.example.majk.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.log_out
import org.example.majk.app.Route
import org.example.majk.core.data.itemsInDrawer
import org.example.majk.core.domain.RouteTitle
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.core.domain.SharedState
import org.jetbrains.compose.resources.stringResource

@Composable
fun Drawer(
    userInfo: SharedState?,
    currentRouteTitle: RouteTitle,
    onSignOutClick: () -> Unit,
    onItemClick: (Route) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        MajkLogo(
            modifier = Modifier
                .size(100.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Cześć, ${userInfo?.username}!",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = DarkTeal
        )
        Spacer(modifier = Modifier.weight(1f))
        itemsInDrawer.forEach { item ->
            DrawerItem(
                item = item,
                userInfo = userInfo,
                selected = currentRouteTitle.title == item.title,
                modifier = Modifier.padding(horizontal = 40.dp),
                onDrawerItemClick = { onItemClick(item.route) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        MajkButton(
            text = stringResource(Res.string.log_out),
            onAction = {
                onSignOutClick()
            },
            boldText = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}