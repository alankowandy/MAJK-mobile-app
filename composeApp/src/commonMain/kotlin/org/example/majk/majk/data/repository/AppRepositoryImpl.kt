package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.majk.majk.data.dto.AdminAuthDto
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.data.dto.MyMedicamentListDto
import org.example.majk.majk.data.dto.UserSettingsDto
import org.example.majk.majk.domain.AdminAuthUsers
import org.example.majk.majk.domain.AppRepository

class AppRepositoryImpl(
    client: SupabaseClient
): AppRepository {

    private val postgrest = client.postgrest

    override suspend fun collectContainerState() {
        TODO("Not yet implemented")
    }

    override suspend fun collectUsers(familyId: Long): List<ManageFamilyDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "collect_profiles",
                parameters = buildJsonObject {
                    put("family_id", familyId)
                }
            ).decodeList<ManageFamilyDto>()
            data
        }
    }

    override suspend fun collectUsersAdminAuth(familyId: Long): List<AdminAuthDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "collect_profiles",
                parameters = buildJsonObject {
                    put("family_id", familyId)
                }
            ).decodeList<AdminAuthDto>()
            data
        }
    }

    override suspend fun fetchUserSettings(userId: Long): UserSettingsDto {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_user_settings",
                parameters = buildJsonObject {
                    put("user_id", userId)
                }
            ).decodeList<UserSettingsDto>()
            data[0]
        }
    }

    override suspend fun updateUserSettings(id: Long, username: String, permission: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_profile",
                parameters = buildJsonObject {
                    put("profile_id_input", id)
                    put("new_name", username)
                    put("new_permission", permission)
                }
            )
        }
    }

    override suspend fun deleteUserProfile(userId: Long, username: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "delete_profile",
                parameters = buildJsonObject {
                    put("user_id_input", userId)
                    put("profile_name_input", username)
                }
            )
        }
    }

    override suspend fun insertLimitedProfile(username: String, uuid: String, familyId: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "insert_limited_profile",
                parameters = buildJsonObject {
                    put("name", username)
                    put("account_id", uuid)
                    put("family_id", familyId)
                }
            )
        }
    }

    override suspend fun fetchMedicamentList(familyId: Long): List<MyMedicamentListDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "fetch_medicament_list",
                parameters = buildJsonObject {
                    put("family_id", familyId)
                }
            ).decodeList<MyMedicamentListDto>()
            data
        }
    }
}