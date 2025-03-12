package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeviceCodeDto(
    @SerialName("id_exists")
    val deviceIdExists: Boolean? = false
)