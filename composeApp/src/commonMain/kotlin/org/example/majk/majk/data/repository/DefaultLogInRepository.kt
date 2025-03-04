package org.example.majk.majk.data.repository

import org.example.majk.majk.domain.LogInRepository

class DefaultLogInRepository: LogInRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(
        username: String,
        email: String,
        password: String,
        familyCode: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun registerDevice(
        username: String,
        email: String,
        password: String,
        deviceCode: String
    ): Boolean {
        TODO("Not yet implemented")
    }

}