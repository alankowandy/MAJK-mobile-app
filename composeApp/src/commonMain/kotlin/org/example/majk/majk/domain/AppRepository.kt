package org.example.majk.majk.domain

import org.example.majk.majk.data.dto.ManageFamilyDto

interface AppRepository {
    suspend fun collectContainerState()
    suspend fun collectUsers(familyId: Long): List<ManageFamilyDto>
}