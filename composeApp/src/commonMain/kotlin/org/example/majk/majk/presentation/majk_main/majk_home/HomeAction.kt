package org.example.majk.majk.presentation.majk_main.majk_home

interface HomeAction {
    data class OnContainerClick(val containerId: Long): HomeAction
    data object OnDismissDialog: HomeAction
    data object OnNextHourCalc: HomeAction
}