package org.example.majk.majk.domain.repository

import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import org.example.majk.core.data.dto.FamilyUsersDto
import org.example.majk.core.data.dto.SharedStateDto
import org.example.majk.majk.data.dto.DeviceCodeDto
import org.example.majk.majk.data.dto.FamilyCodeDto

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String): String
    suspend fun registerDevice(deviceCode: Long)
    suspend fun signOut()
    suspend fun insertNewUser(username: String, familyCode: Long, email: String)
    suspend fun insertContainers(deviceCode: Long)
    suspend fun insertNewUser(username: String)
    suspend fun checkFamilyCode(familyCode: Long): FamilyCodeDto
    suspend fun checkDeviceCode(deviceCode: Long): DeviceCodeDto
    suspend fun insertAdminProfile(username: String, deviceCode: Long, email: String)
    fun sessionStatus(): Flow<SessionStatus>
    suspend fun fetchProfileDetails(email: String, authId: String): SharedStateDto
    suspend fun fetchFamilyUsers(familyId: Long): List<FamilyUsersDto>
}