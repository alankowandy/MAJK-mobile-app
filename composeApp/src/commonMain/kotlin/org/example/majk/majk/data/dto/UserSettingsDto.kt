package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSettingsDto(
    @SerialName("permission")
    val currentPermission: String,

    @SerialName("name")
    val currentUsername: String,

    @SerialName("avatar_color")
    val avatarColor: Int
)