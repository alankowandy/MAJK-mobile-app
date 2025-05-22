package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContainerSettingsSearchQueryDto(
    @SerialName("id")
    val medicamentId: Long,

    @SerialName("nameOfMedicament")
    val medicamentName: String
)