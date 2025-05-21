package org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen

interface ContainerStateAction {
    data object OnRefreshData: ContainerStateAction
    data class OnSettingsClick(val containerId: Long): ContainerStateAction
    data object OnDialogClear: ContainerStateAction
}