package org.example.majk.majk.domain

import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.flow.Flow
import org.example.majk.core.data.SessionState
import org.example.majk.majk.data.dto.DeviceCodeDto
import org.example.majk.majk.data.dto.FamilyCodeDto

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun registerDevice(email: String, password: String)
    suspend fun signOut()
    suspend fun insertNewUsername(username: String, familyCode: Long)
    suspend fun checkFamilyCode(familyCode: Long): FamilyCodeDto
    suspend fun checkDeviceCode(deviceCode: Long): DeviceCodeDto
    fun sessionStatus(): Flow<SessionStatus>

}