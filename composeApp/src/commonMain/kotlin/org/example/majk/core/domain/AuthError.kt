package org.example.majk.core.domain

import org.example.majk.core.domain.Result

sealed class AuthError: Error {
    data object DatabaseError: AuthError()
    data object NetworkError: AuthError()
    data object UnknownError: AuthError()
}