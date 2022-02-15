package com.tick.taku.datastore.encryptedserializer.datasource

import androidx.datastore.core.Serializer
import com.tick.taku.datastore.encryptedserializer.datasource.crypto.DataStoreSerializeCipher
import java.io.InputStream
import java.io.OutputStream

abstract class DataStoreCryptoSerializer <T> (keyAlias: String): Serializer<T> {

    private val cipher = DataStoreSerializeCipher(keyAlias)

    override suspend fun readFrom(input: InputStream): T {
        val decryptedData = cipher.decryptFrom(input)
        return decode(decryptedData.decodeToString())
    }

    abstract fun decode(data: String): T

    override suspend fun writeTo(t: T, output: OutputStream) {
        val encodedData = encode(t).encodeToByteArray()
        cipher.encryptTo(encodedData, output)
    }

    abstract fun encode(data: T): String
}