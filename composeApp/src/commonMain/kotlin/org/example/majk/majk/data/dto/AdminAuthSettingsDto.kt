package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdminAuthSettingsDto(
    @SerialName("id")
    val id: Long?,

    @SerialName("type")
    val type: String?,

    @SerialName("code")
    val code: String?,

    @SerialName("name")
    val username: String?
)