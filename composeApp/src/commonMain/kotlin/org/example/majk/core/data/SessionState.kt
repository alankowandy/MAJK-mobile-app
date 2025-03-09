package org.example.majk.core.data

import io.github.jan.supabase.auth.user.UserSession

data class SessionState(
    val session: UserSession?,
    val userId: String
)