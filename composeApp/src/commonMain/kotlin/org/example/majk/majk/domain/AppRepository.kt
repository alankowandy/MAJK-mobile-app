package org.example.majk.majk.domain

import org.example.majk.majk.data.dto.AdminAuthDto
import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.data.dto.UserSettingsDto

interface AppRepository {
    suspend fun collectContainerState()
    suspend fun collectUsers(familyId: Long): List<ManageFamilyDto>
    suspend fun collectUsersAdminAuth(familyId: Long): List<AdminAuthDto>
    suspend fun fetchUserSettings(userId: Long): List<UserSettingsDto>
    suspend fun insertLimitedProfile(username: String, uuid: String, familyId: Long)
}