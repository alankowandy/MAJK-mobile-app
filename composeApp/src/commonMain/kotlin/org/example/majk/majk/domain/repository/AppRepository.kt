package org.example.majk.majk.domain.repository

import kotlinx.datetime.LocalDate
import org.example.majk.majk.data.dto.AdminAuthDto
import org.example.majk.majk.data.dto.ContainerSettingsDto
import org.example.majk.majk.data.dto.ContainerStateDto
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.data.dto.MyMedicamentListDto
import org.example.majk.majk.data.dto.UserSettingsDto
import org.example.majk.majk.domain.MedicineEntry

interface AppRepository {
    suspend fun collectContainerState()
    suspend fun collectUsers(familyId: Long): List<ManageFamilyDto>
    suspend fun collectUsersAdminAuth(familyId: Long): List<AdminAuthDto>
    suspend fun fetchUserSettings(userId: Long): UserSettingsDto
    suspend fun updateUserSettings(id: Long, username: String, permission: String)
    suspend fun deleteUserProfile(userId: Long, username: String)
    suspend fun insertLimitedProfile(username: String, uuid: String, familyId: Long)
    suspend fun fetchMedicamentList(familyId: Long): List<MyMedicamentListDto>
    suspend fun deleteMedicament(medicamentId: Long)
    suspend fun fetchScheduleForDate(date: LocalDate): Map<Int, List<MedicineEntry>>
    suspend fun fetchContainerState(deviceId: Long): List<ContainerStateDto>
    suspend fun fetchContainerSettings(containerId: Long): ContainerSettingsDto
}