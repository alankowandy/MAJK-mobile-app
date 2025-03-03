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
            supabaseUrl = "https://gaisritsqqjxsfusjkio.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdhaXNyaXRzcXFqeHNmdXNqa2lvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDAxNjUzNDAsImV4cCI6MjA1NTc0MTM0MH0.C68laxL4Nrh8B05GU8gGy3iITSI7aRrjp4Uw8pbWByY"
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