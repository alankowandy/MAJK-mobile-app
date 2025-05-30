package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

interface ContainerSettingsAction {
    data class OnPillQuantityChange(val pillQuantity: String): ContainerSettingsAction
    data class OnSearchQueryChange(val medicamentSearch: String, val medicamentId: Long): ContainerSettingsAction
    data class OnSearchExpandedChange(val isExpanded: Boolean): ContainerSettingsAction
    data object OnEmptyContainerClick: ContainerSettingsAction
    data object OnConfirmClick: ContainerSettingsAction
    data object OnDismissDialog: ContainerSettingsAction
    data object OnBackClick: ContainerSettingsAction
    data object OnAddPillsClick: ContainerSettingsAction
    data object OnSubtractPillsClick: ContainerSettingsAction
    data object OnEmptyPillEntryClick: ContainerSettingsAction
    data object OnConfirmEmptyClick: ContainerSettingsAction
}