package org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.theme.OffWhite
import org.example.majk.majk.domain.MedicamentSearch
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.components.MyMedkitEditLayout
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyMedkitEditScreenRoot(
    viewModel: MyMedkitEditViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()

    MyMedkitEditScreen(
        state = state,
        searchQuery = searchQuery,
        searchResult = searchResult,
        onAction = { action ->
            when (action) {
                is MyMedkitEditAction.OnBackClick -> {
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
fun MyMedkitEditScreen(
    state: MyMedkitEditState,
    searchQuery: String,
    searchResult: List<MedicamentSearch>,
    onAction: (MyMedkitEditAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
        contentAlignment = Alignment.Center
    ) {
        MyMedkitEditLayout(
            state = state,
            searchQuery = searchQuery,
            searchResult = searchResult,
            onAction = onAction
        )
    }
}