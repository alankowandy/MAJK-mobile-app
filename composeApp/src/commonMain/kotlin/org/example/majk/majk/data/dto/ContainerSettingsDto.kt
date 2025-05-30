package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContainerSettingsDto(
    @SerialName("id")
    val containerId: Long,

    @SerialName("state")
    val containerState: String,

    @SerialName("medicament_name")
    val medicamentName: String,

    @SerialName("number")
    val containerNumber: Long,

    @SerialName("container_pill_quantity")
    val pillQuantity: Long
)