package org.example.majk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform