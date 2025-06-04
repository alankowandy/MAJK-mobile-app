package org.example.majk.majk.domain

data class AdminAuthUsers(
    val id: Long,
    val username: String,
    val avatarColor: Int,
    val isNfcChecked: Boolean = false,
    val isRfidChecked: Boolean = false
)