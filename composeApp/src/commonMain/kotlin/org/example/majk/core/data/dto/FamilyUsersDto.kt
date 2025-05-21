package org.example.majk.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FamilyUsersDto(
    @SerialName("id")
    val userId: Long,

    @SerialName("name")
    val username: String,

    @SerialName("permission")
    val permission: String
)
