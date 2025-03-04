package org.example.majk.majk.presentation

import org.example.majk.majk.data.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}