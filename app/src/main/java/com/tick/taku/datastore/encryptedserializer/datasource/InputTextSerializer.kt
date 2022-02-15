package com.tick.taku.datastore.encryptedserializer.datasource

import kotlinx.serialization.StringFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class InputTextSerializer: DataStoreCryptoSerializer<InputText>("input_text") {

    override val defaultValue: InputText
        get() = InputText()

    override fun encode(data: InputText, stringFormat: StringFormat): String = stringFormat.encodeToString(data)

    override fun decode(data: String, stringFormat: StringFormat): InputText = stringFormat.decodeFromString(data)
}