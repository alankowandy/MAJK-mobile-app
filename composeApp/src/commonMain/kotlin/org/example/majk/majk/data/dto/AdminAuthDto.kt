package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdminAuthDto(
    @SerialName("id")
    val userId: Long,

    @SerialName("name")
    val username: String,

    @SerialName("avatar_color")
    val avatarColor: Int
)