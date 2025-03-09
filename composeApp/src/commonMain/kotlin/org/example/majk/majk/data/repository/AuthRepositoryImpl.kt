package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import org.example.majk.core.data.SessionState
import org.example.majk.core.domain.AuthError
import org.example.majk.core.domain.Result
import org.example.majk.majk.domain.AuthRepository

class AuthRepositoryImpl(
    private val client: SupabaseClient
): AuthRepository {
    private val auth = client.auth

    override suspend fun signIn(email: String, password: String): Result<SessionState?, AuthError> {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val session = auth.currentSessionOrNull()
            if (session != null) {
                Result.Success(session.user?.let {
                    SessionState(
                        session = session,
                        userId = it.id
                    )
                })
            } else {
                Result.Error(AuthError.UnknownError)
            }
        } catch (e: AuthRestException) {
            val error = when (e.error) {
                "invalid_grant" -> AuthError.InvalidCredentials
                "network_error" -> AuthError.NetworkError
                else -> AuthError.UnknownError
            }
            Result.Error(error)
        }
    }

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        familyCode: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun registerDevice(
        username: String,
        email: String,
        password: String,
        deviceCode: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkFamilyCode(familyCode: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkDeviceCode(deviceCode: Int): Boolean {
        TODO("Not yet implemented")
    }
}