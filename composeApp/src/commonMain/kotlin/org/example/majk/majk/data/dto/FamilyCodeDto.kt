package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FamilyCodeDto(
    @SerialName("id_exists")
    val familyCodeExists: Boolean? = false
)