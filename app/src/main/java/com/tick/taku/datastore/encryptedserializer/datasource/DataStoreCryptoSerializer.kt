package com.tick.taku.datastore.encryptedserializer.datasource

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.tick.taku.datastore.encryptedserializer.datasource.crypto.DataStoreSerializeCipher
import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

abstract class DataStoreCryptoSerializer <T> (
    keyAlias: String,
    private val stringFormat: StringFormat = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
): Serializer<T> {

    private val cipher = DataStoreSerializeCipher(keyAlias)

    override suspend fun readFrom(input: InputStream): T = runCatching {
        val decryptedData = cipher.decryptFrom(input)
        decode(decryptedData.decodeToString(), stringFormat)
    }.getOrElse {
        if (it is SerializationException) throw CorruptionException("Cannot read proto.", it)
        else throw it
    }

    abstract fun decode(data: String, stringFormat: StringFormat): T

    override suspend fun writeTo(t: T, output: OutputStream) {
        val encodedData = encode(t, stringFormat).encodeToByteArray()
        cipher.encryptTo(encodedData, output)
    }

    abstract fun encode(data: T, stringFormat: StringFormat): String
}