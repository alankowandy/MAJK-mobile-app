package org.example.majk.majk.domain

import org.example.majk.majk.data.dto.ManageFamilyDto
import org.example.majk.majk.data.dto.UserSettingsDto

interface AppRepository {
    suspend fun collectContainerState()
    suspend fun collectUsers(familyId: Long): List<ManageFamilyDto>
    suspend fun fetchUserSettings(userId: Long): List<UserSettingsDto>
}