package org.example.majk.majk.presentation.majk_main.majk_manage_family

sealed interface ManageFamilyAction {
    data object OnScheduleClick: ManageFamilyAction
    data object OnPermissionsClick: ManageFamilyAction
    data object OnDeleteClick: ManageFamilyAction
    data object OnEditClick: ManageFamilyAction
    data object DismissAction: ManageFamilyAction
}