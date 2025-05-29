package org.example.majk.majk.presentation.majk_main.majk_admin_auth

import org.example.majk.majk.domain.AdminAuthUsers

data class AdminAuthState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isHostCardEmulationAvailable: Boolean,
    val users: List<AdminAuthUsers> = emptyList()
)