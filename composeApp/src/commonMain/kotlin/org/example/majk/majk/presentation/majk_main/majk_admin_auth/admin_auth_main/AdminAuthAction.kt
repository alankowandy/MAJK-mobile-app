package org.example.majk.majk.presentation.majk_main.majk_admin_auth.admin_auth_main

interface AdminAuthAction {
    data class OnEditClick(val accountId: Long): AdminAuthAction
    data class OnNfcClick(val accountId: Long, val username: String): AdminAuthAction
    data object OnDismissError: AdminAuthAction
    data class OnRfidClick(val accountId: Long): AdminAuthAction
}