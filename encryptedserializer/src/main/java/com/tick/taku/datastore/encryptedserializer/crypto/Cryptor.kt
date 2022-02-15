package com.tick.taku.datastore.encryptedserializer.crypto

import java.io.InputStream
import java.io.OutputStream

interface Cryptor {

    fun encryptTo(data: ByteArray, outputStream: OutputStream)

    fun decryptFrom(inputStream: InputStream): ByteArray
}