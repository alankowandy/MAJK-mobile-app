package org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen

sealed interface ManageFamilyAction {
    data class OnScheduleClick(val userId: Long): ManageFamilyAction
    data class OnSettingsClick(val userId: Long): ManageFamilyAction
    data object OnRefreshData: ManageFamilyAction
    data object OnDismissDialog: ManageFamilyAction
}