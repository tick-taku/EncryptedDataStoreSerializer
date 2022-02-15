package com.tick.taku.datastore.encryptedserializer.datasource

import kotlinx.serialization.Serializable

@Serializable
data class InputText(
    val text: String = ""
)