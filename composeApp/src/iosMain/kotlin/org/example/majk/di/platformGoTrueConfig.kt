package org.example.majk.di

import io.github.jan.supabase.auth.AuthConfig

actual fun AuthConfig.platformGoTrueConfig() {
    scheme = "io.jan.supabase"
    host = "login"
}