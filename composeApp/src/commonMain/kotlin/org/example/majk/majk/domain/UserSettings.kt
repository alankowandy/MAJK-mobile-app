package org.example.majk.majk.domain

data class UserSettings(
    val currentPermission: String = "limited",
    val currentUsername: String = "",
    val avatarColor: Int = -1
)