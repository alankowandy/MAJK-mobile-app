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
    val pillQuantity: Long,

    @SerialName("number")
    val containerNumber: Long,

    @SerialName("nameOfMedicament")
    val medicamentName: String?
)