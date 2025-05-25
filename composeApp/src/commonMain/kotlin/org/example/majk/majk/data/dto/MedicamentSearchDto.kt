package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MedicamentSearchDto(
    @SerialName("id")
    val medicamentId: Long,

    @SerialName("name")
    val medicamentName: String,

    @SerialName("type")
    val medicamentType: String,

    @SerialName("leaflet")
    val medicamentLeaflet: String
)