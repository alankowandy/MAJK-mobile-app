package org.example.majk.majk.presentation.majk_main.majk_admin_auth

interface AdminAuthAction {
    data object OnRfidClick: AdminAuthAction
    data object OnNfcClick: AdminAuthAction
}