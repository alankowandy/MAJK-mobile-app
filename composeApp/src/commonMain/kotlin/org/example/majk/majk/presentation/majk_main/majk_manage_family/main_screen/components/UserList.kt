package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.components

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
import org.example.majk.majk.domain.ManageFamily

@Composable
fun UserList(
    users: List<ManageFamily>,
    onScheduleClick: (Long) -> Unit,
    onSettingsClick: (Long) -> Unit,
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
            UserTile(
                username = user.username,
                userColor = user.avatarColor,
                onScheduleClick = { onScheduleClick(user.id) },
                onSettingsClick = { onSettingsClick(user.id) }
            )
        }
    }
}