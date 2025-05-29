package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import majk.composeapp.generated.resources.Res
import majk.composeapp.generated.resources.empty_list
import majk.composeapp.generated.resources.error_unknown
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.presentation.components.MajkAlertDialog
import org.example.majk.majk.presentation.majk_main.components.EmptyListText
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.components.MedicineList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduledMedicineListScreenRoot(
    viewModel: ScheduledMedicineListViewModel = koinViewModel(),
    onMedicineDetailsClick: (MedicineEntry) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.errorMessage != null) {
        MajkAlertDialog(
            error = state.errorMessage ?: stringResource(Res.string.error_unknown),
            dismissAction = { viewModel.onAction(ScheduledMedicineListAction.OnDismissDialog) }
        )
    }

    ScheduledMedicineListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ScheduledMedicineListAction.OnNoteDetailsClick -> {
                    onMedicineDetailsClick(action.medicine)
                }
                is ScheduledMedicineListAction.OnBackClick -> {
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
fun ScheduledMedicineListScreen(
    state: ScheduledMedicineListState,
    onAction: (ScheduledMedicineListAction) -> Unit
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
        } else if (state.medicineList.isEmpty()) {
            EmptyListText(modifier = Modifier.align(Alignment.Center))
        }
        else {
            MedicineList(
                state = state,
                onAction = onAction,
                medicineList = state.medicineList
            )
        }
    }
}