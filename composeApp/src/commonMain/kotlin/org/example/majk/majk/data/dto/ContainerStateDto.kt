package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContainerStateDto(
    @SerialName("id")
    val containerId: Long,

    @SerialName("state")
    val containerState: String,

    @SerialName("numberOfPills")
    val pillQuantity: Double,

    @SerialName("number")
    val containerNumber: Long,

    @SerialName("medicamentName")
    val medicamentName: String
)