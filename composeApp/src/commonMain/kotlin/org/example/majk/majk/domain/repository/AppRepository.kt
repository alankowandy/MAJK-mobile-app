package org.example.majk.majk.domain.repository

import kotlinx.datetime.LocalDate
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
import org.example.majk.majk.domain.MedicineEntry

interface AppRepository {
    suspend fun collectContainerState()
    suspend fun collectUsers(familyId: Long): List<ManageFamilyDto>
    suspend fun collectUsersAdminAuth(familyId: Long): List<AdminAuthDto>
    suspend fun fetchUserSettings(userId: Long): UserSettingsDto
    suspend fun updatePermission(id: Long, permission: String)
    suspend fun updateUsername(userId: Long, username: String)
    suspend fun deleteUserProfile(userId: Long, username: String)
    suspend fun insertLimitedProfile(username: String, uuid: String, familyId: Long, email: String)
    suspend fun fetchMedicamentList(familyId: Long): List<MyMedicamentListDto>
    suspend fun deleteMedicament(medicamentId: Long)
    suspend fun fetchReleaseSchedule(accountId: Long): List<ReleaseScheduleDto>
    suspend fun fetchContainerState(deviceId: Long): List<ContainerStateDto>
    suspend fun fetchContainerSettings(containerId: Long): ContainerSettingsDto
    suspend fun fetchMyMedicament(familyId: Long): List<ContainerSettingsSearchQueryDto>
    suspend fun searchMedicament(familyId: Long, partialName: String): List<ContainerSettingsSearchQueryDto>
    suspend fun updateContainerMedicament(containerId: Long, medicamentId: Long)
    suspend fun updateNumberOfPills(containerId: Long, numberOfPills: Long, state: String)
    suspend fun searchMedicamentSet(partialName: String): List<MedicamentSearchDto>
    suspend fun fetchInitialMedicamentSet(): List<MedicamentSearchDto>
    suspend fun insertMedicament(medicamentId: Long, familyId: Long)
    suspend fun fetchMedicamentEntries(accountId: Long): List<MedicineEntryDto>
    suspend fun deleteScheduledMedicine(releaseId: Long)
    suspend fun updateNote(releaseId: Long, note: String)
    suspend fun fetchFamilyId(accountId: Long): FetchFamilyIdDto
    suspend fun insertNewSchedule(medicamentId: Long, accountId: Long, startDate: String, endDate: String,
                                  dayInterval: Long, pillAmount: Long, consumption: String, note: String)
    suspend fun updateSchedule(releaseId: Long, medicamentId: Long, startDate: String, endDate: String,
                               dayInterval: Long, pillAmount: Long, consumption: String, note: String)
    suspend fun fetchReleaseHistory(accountId: Long): List<ReleaseHistoryDto>
    suspend fun emptyContainer(containerId: Long)
    suspend fun changeProfileColor(accountId: Long, color: Int)
    suspend fun fetchAuthUserSettings(accountId: Long): List<AdminAuthSettingsDto>
}