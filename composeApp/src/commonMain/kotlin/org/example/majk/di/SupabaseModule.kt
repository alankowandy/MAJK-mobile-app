package org.example.majk.di

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.AuthConfig
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.logging.LogLevel
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import org.koin.dsl.module

expect fun AuthConfig.platformGoTrueConfig()

val supabaseModule = module {
    single {
        createSupabaseClient(
            supabaseUrl = "",
            supabaseKey = ""
        ) {
            defaultLogLevel = LogLevel.DEBUG
            install(Postgrest)
            install(Auth) {
                platformGoTrueConfig()
                flowType = FlowType.PKCE
            }
            install(Realtime)
        }
    }
}