package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.majk.majk.data.dto.ManageFamilyDto
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
                function = "",
                parameters = buildJsonObject {
                    put("familyId", familyId)
                }
            ).decodeList<ManageFamilyDto>()
            data
        }
    }
}