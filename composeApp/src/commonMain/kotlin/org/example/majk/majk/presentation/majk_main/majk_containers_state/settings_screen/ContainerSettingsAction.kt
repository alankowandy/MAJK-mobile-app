package org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen

interface ContainerSettingsAction {
    data class OnPillQuantityChange(val pillQuantity: Int): ContainerSettingsAction
    data class OnSearchQueryChange(val medicamentSearch: String): ContainerSettingsAction
    data class OnSearchExpandedChange(val isExpanded: Boolean): ContainerSettingsAction
    data object OnEmptyContainerClick: ContainerSettingsAction
    data object OnConfirmClick: ContainerSettingsAction
    data object OnDismissClick: ContainerSettingsAction
    data object OnDialogueClear: ContainerSettingsAction
    data object OnBackClick: ContainerSettingsAction
}