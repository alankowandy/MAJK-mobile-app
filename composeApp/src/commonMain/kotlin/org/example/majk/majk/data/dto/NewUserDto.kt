package org.example.majk.majk.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewUserDto(

    @SerialName("new_username")
    val username: String? = null,

    @SerialName("new_family_id")
    val familyCode: Long? = null
)