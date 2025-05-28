package org.example.majk.majk.presentation.majk_main.majk_admin_auth

interface AdminAuthAction {
    data class OnNfcClick(val accountId: Long, val username: String): AdminAuthAction
    data object OnDismissError: AdminAuthAction
}