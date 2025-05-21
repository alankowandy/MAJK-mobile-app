package org.example.majk.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SharedStateDto(
    @SerialName("id")
    val accountId: Long? = null,

    @SerialName("name")
    val username: String? = null,

    @SerialName("familyId")
    val familyId: Long? = null,

    @SerialName("deviceId")
    val deviceId: Long? = null,

    @SerialName("permission")
    val permission: String? = null
)