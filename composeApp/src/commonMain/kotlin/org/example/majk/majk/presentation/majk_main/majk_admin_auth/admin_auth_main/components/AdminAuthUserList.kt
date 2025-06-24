package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.domain.AdminAuthUsers
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.AdminAuthAction
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main.AdminAuthState

@Composable
fun AdminAuthUserList(
    state: AdminAuthState,
    users: List<AdminAuthUsers>,
    onAction: (AdminAuthAction) -> Unit,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = users,
            key = { it.id }
        ) { user ->
            AdminAuthUserTile(
                state = state,
                user = user,
                userColor = user.avatarColor,
                onAction = onAction
            )
        }
    }
}