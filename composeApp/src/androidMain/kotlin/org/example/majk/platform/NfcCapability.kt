package org.example.majk.platform

import android.content.Context
import android.nfc.NfcAdapter

actual object NfcCapability {
    fun init(appContext: Context) {
        context = appContext.applicationContext     // keep the safe global ref
    }

    private var context: Context? = null

    actual val isHostCardEmulationAvailable: Boolean
        get() = context?.let { ctx ->
            NfcAdapter.getDefaultAdapter(ctx)?.isEnabled == true &&
                    ctx.packageManager.hasSystemFeature("android.hardware.nfc.hce")
        } ?: false
}