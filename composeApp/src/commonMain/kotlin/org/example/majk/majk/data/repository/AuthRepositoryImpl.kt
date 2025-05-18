package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.majk.core.data.dto.SharedStateDto
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
    ): String {
        return withContext(Dispatchers.IO) {
            val data = auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            data?.id ?: ""
        }
    }

    override suspend fun registerDevice(
        deviceCode: Long
    ) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "register_new_device",
                parameters = buildJsonObject {
                    put("new_device_id", deviceCode)
                }
            )
        }
    }

    override suspend fun insertNewUser(username: String, familyCode: Long, email: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "add_user_profile",
                parameters = buildJsonObject {
                    put("new_username", username)
                    put("new_family_id", familyCode)
                    put("new_email", email)
                }
            )
        }
    }



    override suspend fun insertContainers(deviceCode: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "insert_containers",
                parameters = buildJsonObject {
                    put("new_device_id", deviceCode)
                }
            )
        }
    }

    override suspend fun insertAdminProfile(username: String, deviceCode: Long, email: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "insert_admin_profile_with_email_and_initialize",
                parameters = buildJsonObject {
                    put("new_name", username)
                    put("device_id_input", deviceCode)
                    put("email_input", email)
                }
            )
        }
    }

    override suspend fun insertNewUser(username: String) {

    }

    override suspend fun checkFamilyCode(familyCode: Long): FamilyCodeDto {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "check_family_exists",
                parameters = buildJsonObject {
                    put("family_code", familyCode)
                }
            ).decodeSingle<FamilyCodeDto>()
            data
        }
    }

    override suspend fun checkDeviceCode(deviceCode: Long): DeviceCodeDto {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "check_device_exists",
                parameters = buildJsonObject {
                    put("device_id_input", deviceCode)
                }
            ).decodeList<DeviceCodeDto>()
            data[0]
        }
    }

    override fun sessionStatus(): Flow<SessionStatus> {
        return auth.sessionStatus
    }

    override suspend fun fetchProfileDetails(email: String, authId: String): SharedStateDto {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_and_update_profile_info_by_email",
                parameters = buildJsonObject {
                    put("provided_email", email)
                    put("provided_account_id", authId)
                }
            ).decodeList<SharedStateDto>()
            data[0]
        }
    }

    override suspend fun signOut() {
        return withContext(Dispatchers.IO) {
            auth.signOut()
        }
    }
}