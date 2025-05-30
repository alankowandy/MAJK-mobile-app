package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyMedicamentListDto(
    @SerialName("id")
    val medicamentId: Long,

    @SerialName("medicament_name")
    val medicamentName: String,

    @SerialName("type")
    val medicamentType: String,

    @SerialName("leaflet")
    val leafletUrl: String
)