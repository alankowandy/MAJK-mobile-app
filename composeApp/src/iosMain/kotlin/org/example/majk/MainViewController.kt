package org.example.majk

import androidx.compose.ui.window.ComposeUIViewController
import org.example.majk.app.App
import org.example.majk.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}