package com.tick.taku.datastore.encryptedserializer.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class TextProtoDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<InputText> by dataStore(
        fileName = "text.pb",
        serializer = InputTextSerializer()
    )

    suspend fun save(value: String) {
        context.dataStore.updateData {
            it.copy(text = value)
        }
    }

    fun load() = context.dataStore.data.map { it.text }
}