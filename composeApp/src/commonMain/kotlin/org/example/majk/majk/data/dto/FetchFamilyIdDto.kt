package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchFamilyIdDto(
    @SerialName("familyId")
    val familyId: Long
)
