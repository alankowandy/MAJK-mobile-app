package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.data.dto.UserSettingsDto
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

    override suspend fun fetchUserSettings(userId: Long): List<UserSettingsDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_user_settings",
                parameters = buildJsonObject {
                    put("user_id", userId)
                }
            ).decodeList<UserSettingsDto>()
            data
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
}