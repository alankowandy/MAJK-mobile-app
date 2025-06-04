package org.example.majk.majk.presentation.majk_main.majk_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.majk.core.presentation.OffWhite
import org.example.majk.majk.presentation.majk_main.majk_home.HomeAction
import org.example.majk.majk.presentation.majk_main.majk_home.HomeState

@Composable
fun HomeScreenLayout(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DateBox()

        NextDoseBox(
            state = state,
            releaseSchedule = state.releaseSchedule,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        SkippedDoseBox(
            state = state,
            releaseSchedule = state.releaseSchedule,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        ContainerStateBox(
            containerList = state.containerList,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}