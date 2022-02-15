package com.tick.taku.datastore.encryptedserializer.datasource

import androidx.datastore.core.CorruptionException
import com.tick.taku.datastore.encryptedserializer.DataStoreCryptoSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InputTextSerializer(
    private val stringFormat: StringFormat = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
): DataStoreCryptoSerializer<InputText>("input_text") {

    override val defaultValue: InputText
        get() = InputText()

    override fun encodeTo(data: InputText): String = stringFormat.encodeToString(data)

    override fun decodeFrom(data: String): InputText = runCatching {
        stringFormat.decodeFromString(data) as InputText
    }.getOrElse {
        if (it is SerializationException) throw CorruptionException("Cannot read proto.", it)
        else throw it
    }
}