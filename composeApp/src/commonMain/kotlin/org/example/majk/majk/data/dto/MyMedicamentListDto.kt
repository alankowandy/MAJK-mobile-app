package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyMedicamentListDto(
    @SerialName("id")
    val medicamentId: Long,

    @SerialName("nameOfMedicament")
    val medicamentName: String,

    @SerialName("leaflet")
    val leafletUrl: String
)