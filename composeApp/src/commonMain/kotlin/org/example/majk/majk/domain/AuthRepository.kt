package org.example.majk.majk.domain

import org.example.majk.core.data.SessionState
import org.example.majk.core.domain.AuthError
import org.example.majk.core.domain.Result

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<SessionState?, AuthError>
    suspend fun signUp(username: String, email: String, password: String, familyCode: String): Boolean
    suspend fun registerDevice(username: String, email: String, password: String, deviceCode: String): Boolean
    suspend fun insertNewUsername(username: String, familyCode: Long): Boolean
    suspend fun checkFamilyCode(familyCode: Int): Boolean
    suspend fun checkDeviceCode(deviceCode: Int): Boolean
}