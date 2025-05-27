package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MedicineEntryDto(
    @SerialName("id")
    val releaseId: Long,

    @SerialName("medicamentId")
    val medicamentId:Long,

    @SerialName("nameOfMedicament")
    val medicamentName: String,

    @SerialName("startDate")
    val startDate: String,

    @SerialName("endDate")
    val endDate: String,

    @SerialName("repeatDays")
    val repeatingInterval: Long,

    @SerialName("pillAmount")
    val pillAmount: Long,

    @SerialName("takingWithMeal")
    val mealDependability: String,

    @SerialName("note")
    val note: String?,

    @SerialName("leaflet")
    val leafletUrl: String
)
