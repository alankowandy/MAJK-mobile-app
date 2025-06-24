package org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.theme.DarkTeal
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.components.AddScheduleScreenLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddScheduleScreenRoot(
    viewModel: AddScheduleViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    AddScheduleScreen(
        state = state,
        searchQuery = searchQuery,
        onAction = { action ->
            when (action) {
                is AddScheduleAction.OnBackClick -> {
                    onBackClick()
                }
                else -> {
                    viewModel.onAction(action)
                }
            }
        }
    )
}

@Composable
fun AddScheduleScreen(
    state: AddScheduleState,
    onAction: (AddScheduleAction) -> Unit,
    searchQuery: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = DarkTeal,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } else {
            AddScheduleScreenLayout(
                state = state,
                onAction = onAction,
                searchQuery = searchQuery
            )
        }
    }
}