package org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.majk.core.presentation.DarkTeal
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.domain.MedicineEntry
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.components.MedicineList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduledMedicineListScreenRoot(
    viewModel: ScheduledMedicineListViewModel = koinViewModel(),
    onMedicineDetailsClick: (MedicineEntry) -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val medicineList by viewModel.medicineList.collectAsStateWithLifecycle()

    ScheduledMedicineListScreen(
        state = state,
        medicineList = medicineList.sortedBy { it.releaseId },
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
    onAction: (ScheduledMedicineListAction) -> Unit,
    medicineList: List<MedicineEntry>
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
            MedicineList(
                state = state,
                onAction = onAction,
                medicineList = medicineList
            )
        }
    }
}