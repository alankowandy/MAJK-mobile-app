package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContainerSettingsDto(
    @SerialName("medicamentName")
    val medicamentName: String,

    @SerialName("number")
    val containerNumber: Long,

    @SerialName("numberOfPills")
    val pillQuantity: Double
)