package org.example.majk

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import io.ktor.util.hex

class NfcCardService: HostApduService() {

    companion object {
        /** Card identifier **/
        private const val AID = "F0394148148100"

        private val SELECT_APDU_HEADER = hex("00A40400")
        private val SELECT_APDU = (SELECT_APDU_HEADER +
                byteArrayOf((AID.length / 2).toByte())) +
                hex(AID)

        private const val INS_GET_DATA: Byte = 0xCA.toByte()

        private val STATUS_OK = hex("9000")
        private val STATUS_FAILED = hex("6F00")
        private val STATUS_INS_NOT_SUPPORTED = hex("6D00")
    }

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {
        val hex = hex(commandApdu)

        if (commandApdu.contentEquals(SELECT_APDU)) {
            return STATUS_OK
        }

        if (commandApdu.size >= 4 && commandApdu[1] == INS_GET_DATA) {
            val payload = (getUserId() + "|" + getUsername()).encodeToByteArray()

            return payload + STATUS_OK
        }

        return STATUS_INS_NOT_SUPPORTED
    }

    override fun onDeactivated(reason: Int) { /* no-op */ }

    private fun getUserId(): String = UserCache.userId
    private fun getUsername(): String = UserCache.username
}

object UserCache {
    var userId: String = "0"
    var username: String = "Anon"
}