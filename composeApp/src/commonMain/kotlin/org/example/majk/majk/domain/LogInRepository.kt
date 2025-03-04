package org.example.majk.majk.domain

import io.github.jan.supabase.exceptions.RestException
import org.example.majk.core.data.UserSession

interface LogInRepository {
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(username: String, email: String, password: String, familyCode: String): Boolean
    suspend fun registerDevice(username: String, email: String, password: String, deviceCode: String): Boolean
}