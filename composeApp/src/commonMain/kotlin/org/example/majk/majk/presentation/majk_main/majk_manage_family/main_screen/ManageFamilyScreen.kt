package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.map
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.domain.ManageFamily
import org.example.majk.majk.presentation.majk_main.majk_manage_family.components.UserList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ManageFamilyScreenRoot(
    viewModel: ManageFamilyViewModel = koinViewModel(),
    onScheduleClick: (Long) -> Unit,
    onSettingsClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val users by viewModel.users.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        println(lifecycleState.toString())
        if (lifecycleState == Lifecycle.State.STARTED && state.initialLoadDone) {
            viewModel.onAction(ManageFamilyAction.OnRefreshData)
        }
    }

    ManageFamilyScreen(
        users = users,
        state = state,
        onAction = { action ->
            when (action) {
                is ManageFamilyAction.OnScheduleClick -> onScheduleClick(action.userId)
                is ManageFamilyAction.OnSettingsClick -> onSettingsClick(action.userId)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun ManageFamilyScreen(
    users: List<ManageFamily>,
    state: ManageFamilyState,
    onAction: (ManageFamilyAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = OffWhite),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = DarkTeal
            )
        } else {
            UserList(
                users = users,
                onScheduleClick = {
                    onAction(ManageFamilyAction.OnScheduleClick(it))
                },
                onSettingsClick = {
                    onAction(ManageFamilyAction.OnSettingsClick(it))
                }
            )
        }
    }
}