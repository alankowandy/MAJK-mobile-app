package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_settings

interface AdminAuthSettingsAction {
    data object OnNfcCheckedChange: AdminAuthSettingsAction
    data object OnRfidCheckedChange: AdminAuthSettingsAction
    data class OnRfidCodeEntryChange(val rfidCode: String): AdminAuthSettingsAction
    data object OnSaveClick: AdminAuthSettingsAction
}