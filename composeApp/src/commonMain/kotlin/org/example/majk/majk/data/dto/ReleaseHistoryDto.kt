package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseHistoryDto(
    @SerialName("pill_release_id")
    val releaseId: Long,

    @SerialName("medicament_id")
    val medicamentId: Long,

    @SerialName("name_of_medicament")
    val medicamentName: String,

    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String,

    @SerialName("repeat_days")
    val dayInterval: Long,

    @SerialName("pill_amount")
    val pillAmount: Long,

    @SerialName("history_id")
    val releaseHistoryId: Long,

    @SerialName("taking_time")
    val releaseHistoryDate: String
)
