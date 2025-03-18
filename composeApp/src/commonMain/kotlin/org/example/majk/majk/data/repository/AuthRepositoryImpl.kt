package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.majk.core.data.SessionState
import org.example.majk.core.domain.AuthError
import org.example.majk.core.domain.Result
import org.example.majk.majk.data.dto.DeviceCodeDto
import org.example.majk.majk.data.dto.FamilyCodeDto
import org.example.majk.majk.domain.AuthRepository

class AuthRepositoryImpl(
    client: SupabaseClient
): AuthRepository {
    private val auth = client.auth
    private val postgrest = client.postgrest

    override suspend fun signIn(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ) {
        return withContext(Dispatchers.IO) {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun registerDevice(
        email: String,
        password: String
    ) {
        return withContext(Dispatchers.IO) {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun insertNewUsername(username: String, familyCode: Long) {
        return withContext(Dispatchers.IO) {
            try {
                postgrest.rpc(
                    function = "insertNewUsername",
                    parameters = buildJsonObject {
                        put("new_username", username)
                        put("new_family_id", familyCode)
                    }
                )
            } catch (e: RestException) {
                Result.Error(AuthError.DatabaseError)
            } catch (e: HttpRequestException) {
                Result.Error(AuthError.NetworkError)
            }
        }
    }

    override suspend fun checkFamilyCode(familyCode: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "checkFamilyCode",
                parameters = buildJsonObject {
                    put("family_code", familyCode)
                }
            ).decodeSingle<Boolean>()
            data
        }
    }

    override suspend fun checkDeviceCode(deviceCode: Long): DeviceCodeDto {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "checkDeviceCode",
                parameters = buildJsonObject {
                    put("id", deviceCode)
                }
            ).decodeSingle<DeviceCodeDto>()
        }
    }

    override fun sessionStatus(): Flow<SessionStatus> {
        return auth.sessionStatus
    }

    override suspend fun signOut() {
        return withContext(Dispatchers.IO) {
            auth.signOut()
        }
    }
}