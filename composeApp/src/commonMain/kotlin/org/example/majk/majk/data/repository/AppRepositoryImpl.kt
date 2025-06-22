package org.example.majk.majk.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.majk.majk.data.dto.AdminAuthDto
import org.example.majk.majk.data.dto.AdminAuthSettingsDto
import org.example.majk.majk.data.dto.ContainerSettingsDto
import org.example.majk.majk.data.dto.ContainerSettingsSearchQueryDto
import org.example.majk.majk.data.dto.ContainerStateDto
import org.example.majk.majk.data.dto.FetchFamilyIdDto
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.data.dto.MedicamentSearchDto
import org.example.majk.majk.data.dto.MedicineEntryDto
import org.example.majk.majk.data.dto.MyMedicamentListDto
import org.example.majk.majk.data.dto.ReleaseHistoryDto
import org.example.majk.majk.data.dto.ReleaseScheduleDto
import org.example.majk.majk.data.dto.UserSettingsDto
import org.example.majk.majk.domain.repository.AppRepository
import org.example.majk.majk.domain.MedicineEntry

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
                    put("family_id_input", familyId)
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
                    put("family_id_input", familyId)
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
                    put("profile_id_input", userId)
                }
            ).decodeList<UserSettingsDto>()
            data[0]
        }
    }

    override suspend fun updatePermission(id: Long, permission: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_permission",
                parameters = buildJsonObject {
                    put("user_id", id)
                    put("new_permission", permission)
                }
            )
        }
    }

    override suspend fun updateUsername(userId: Long, username: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_username",
                parameters = buildJsonObject {
                    put("userid", userId)
                    put("new_username", username)
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

    override suspend fun insertLimitedProfile(username: String, uuid: String, familyId: Long, email: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "insert_limited_profile",
                parameters = buildJsonObject {
                    put("name", username)
                    put("account_id", uuid)
                    put("family_id", familyId)
                    put("email_input", email)
                }
            )
        }
    }

    override suspend fun fetchMedicamentList(familyId: Long): List<MyMedicamentListDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "fetch_my_medicament_list",
                parameters = buildJsonObject {
                    put("family_id", familyId)
                }
            ).decodeList<MyMedicamentListDto>()
            data
        }
    }

    override suspend fun deleteMedicament(medicamentId: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "delete_medicament",
                parameters = buildJsonObject {
                    put("medicament_id_input", medicamentId)
                }
            )
        }
    }

    override suspend fun fetchReleaseSchedule(accountId: Long): List<ReleaseScheduleDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "fetch_pill_release_with_history",
                parameters = buildJsonObject {
                    put("profile_id_input", accountId)
                }
            ).decodeList<ReleaseScheduleDto>()
            data
        }
    }

    override suspend fun fetchContainerState(deviceId: Long): List<ContainerStateDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_containers_with_medicament_names",
                parameters = buildJsonObject {
                    put("device_id_input", deviceId)
                }
            ).decodeList<ContainerStateDto>()
            data
        }
    }

    override suspend fun fetchContainerSettings(containerId: Long): ContainerSettingsDto {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_container_details_by_id",
                parameters = buildJsonObject {
                    put("container_id_input", containerId)
                }
            ).decodeList<ContainerSettingsDto>()
            data[0]
        }
    }

    override suspend fun fetchMyMedicament(familyId: Long): List<ContainerSettingsSearchQueryDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_medicaments_by_family_id",
                parameters = buildJsonObject {
                    put("family_id_input", familyId)
                }
            ).decodeList<ContainerSettingsSearchQueryDto>()
            data
        }
    }

    override suspend fun searchMedicament(familyId: Long, partialName: String): List<ContainerSettingsSearchQueryDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "search_medicaments_by_family_and_partial_name",
                parameters = buildJsonObject {
                    put("family_id_input", familyId)
                    put("partial_name_input", partialName)
                }
            ).decodeList<ContainerSettingsSearchQueryDto>()
            data
        }
    }

    override suspend fun updateContainerMedicament(containerId: Long, medicamentId: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_container_medicament",
                parameters = buildJsonObject {
                    put("container_id_input", containerId)
                    put("medicament_id_input", medicamentId)
                }
            )
        }
    }

    override suspend fun updateNumberOfPills(containerId: Long, numberOfPills: Long, state: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_number_of_pills",
                parameters = buildJsonObject {
                    put("container_id", containerId)
                    put("new_number_of_pills", numberOfPills)
                    put("new_state", state)
                }
            )
        }
    }

    override suspend fun searchMedicamentSet(partialName: String): List<MedicamentSearchDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "search_medicament_set_by_partial_name",
                parameters = buildJsonObject {
                    put("partial_name_input", partialName)
                }
            ).decodeList<MedicamentSearchDto>()
            data
        }
    }

    override suspend fun fetchInitialMedicamentSet(): List<MedicamentSearchDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_top_50_medicament_set"
            ).decodeList<MedicamentSearchDto>()
            data
        }
    }

    override suspend fun insertMedicament(medicamentId: Long, familyId: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "insert_medicament_from_set",
                parameters = buildJsonObject {
                    put("medicament_set_id_input", medicamentId)
                    put("family_id_input", familyId)
                }
            )
        }
    }

    override suspend fun fetchMedicamentEntries(accountId: Long): List<MedicineEntryDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_full_pill_schedule_by_profile",
                parameters = buildJsonObject {
                    put("profile_id_input", accountId)
                }
            ).decodeList<MedicineEntryDto>()
            data
        }
    }

    override suspend fun deleteScheduledMedicine(releaseId: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "delete_pill_release_by_id",
                parameters = buildJsonObject {
                    put("pill_release_id_input", releaseId)
                }
            )
        }
    }

    override suspend fun updateNote(releaseId: Long, note: String) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_pill_release_note",
                parameters = buildJsonObject {
                    put("pill_release_id_input", releaseId)
                    put("note_input", note)
                }
            )
        }
    }

    override suspend fun fetchFamilyId(accountId: Long): FetchFamilyIdDto {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_family_id_by_profile_id",
                parameters = buildJsonObject {
                    put("profile_id_input", accountId)
                }
            ).decodeList<FetchFamilyIdDto>()
            data[0]
        }
    }

    override suspend fun insertNewSchedule(
        medicamentId: Long,
        accountId: Long,
        startDate: String,
        endDate: String,
        dayInterval: Long,
        pillAmount: Long,
        consumption: String,
        note: String
    ) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "insert_pill_release",
                parameters = buildJsonObject {
                    put("medicament_id_input", medicamentId)
                    put("profile_id_input", accountId)
                    put("start_date_input", startDate)
                    put("end_date_input", endDate)
                    put("repeat_days_input", dayInterval)
                    put("pill_amount_input", pillAmount)
                    put("taking_with_meal_input", consumption)
                    put("note_input", note)
                }
            )
        }
    }

    override suspend fun updateSchedule(
        releaseId: Long,
        medicamentId: Long,
        startDate: String,
        endDate: String,
        dayInterval: Long,
        pillAmount: Long,
        consumption: String,
        note: String
    ) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_pill_release_by_id",
                parameters = buildJsonObject {
                    put("pill_release_id_input", releaseId)
                    put("medicament_id_input", medicamentId)
                    put("start_date_input", startDate)
                    put("end_date_input", endDate)
                    put("repeat_days_input", dayInterval)
                    put("pill_amount_input", pillAmount)
                    put("taking_with_meal_input", consumption)
                    put("note_input", note)
                }
            )
        }
    }

    override suspend fun fetchReleaseHistory(accountId: Long): List<ReleaseHistoryDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_pill_release_with_history",
                parameters = buildJsonObject {
                    put("profile_id_input", accountId)
                }
            ).decodeList<ReleaseHistoryDto>()
            data
        }
    }

    override suspend fun emptyContainer(containerId: Long) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "empty_the_container",
                parameters = buildJsonObject {
                    put("container_id", containerId)
                }
            )
        }
    }

    override suspend fun changeProfileColor(accountId: Long, color: Int) {
        return withContext(Dispatchers.IO) {
            postgrest.rpc(
                function = "update_avatar_color",
                parameters = buildJsonObject {
                    put("updated_color", color)
                    put("acc_id", accountId)
                }
            )
        }
    }

    override suspend fun fetchAuthUserSettings(accountId: Long): List<AdminAuthSettingsDto> {
        return withContext(Dispatchers.IO) {
            val data = postgrest.rpc(
                function = "get_authentication_by_profile_id",
                parameters = buildJsonObject {
                    put("profile_id_input", accountId)
                }
            ).decodeList<AdminAuthSettingsDto>()
            data
        }
    }
}