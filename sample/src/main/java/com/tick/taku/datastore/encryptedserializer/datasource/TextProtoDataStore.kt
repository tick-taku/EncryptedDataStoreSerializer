package com.tick.taku.datastore.encryptedserializer.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class TextProtoDataStore(
    private val context: Context,
    serializer: Serializer<InputText>
) {

    private val Context.dataStore: DataStore<InputText> by dataStore(
        fileName = "text.pb",
        serializer = serializer
    )

    suspend fun save(value: String) {
        context.dataStore.updateData {
            it.copy(text = value)
        }
    }

    fun load() = context.dataStore.data.map { it.text }
}