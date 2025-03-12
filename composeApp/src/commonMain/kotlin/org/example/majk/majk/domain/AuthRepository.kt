package org.example.majk.majk.domain

import org.example.majk.majk.data.dto.DeviceCodeDto
import org.example.majk.majk.data.dto.FamilyCodeDto

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun registerDevice(email: String, password: String)
    suspend fun insertNewUsername(username: String, familyCode: Long)
    suspend fun checkFamilyCode(familyCode: Long): FamilyCodeDto
    suspend fun checkDeviceCode(deviceCode: Long): DeviceCodeDto
}