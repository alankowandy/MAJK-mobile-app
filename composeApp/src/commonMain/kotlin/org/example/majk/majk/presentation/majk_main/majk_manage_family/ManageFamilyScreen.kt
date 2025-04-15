package org.example.majk.majk.presentation.majk_main.majk_manage_family

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.ManageFamily
import org.example.majk.majk.presentation.majk_main.majk_manage_family.components.UserTile
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManageFamilyScreenRoot(
    viewModel: ManageFamilyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val users by viewModel.users.collectAsStateWithLifecycle()

    ManageFamilyScreen(
        users = users,
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ManageFamilyScreen(
    users: List<ManageFamily>,
    state: ManageFamilyState,
    onAction: (ManageFamilyAction) -> Unit,
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
                userColor = OffWhite,
                onScheduleClick = {},
                onPermissionsClick = {},
                onEditClick = {},
                onDeleteClick = {}
            )
        }
    }
}