package com.tick.taku.datastore.encryptedserializer.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextPreferencesDataStore(private val context: Context) {

    companion object {
        val textKey = stringPreferencesKey("text")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "text"
    )

    suspend fun save(value: String) {
        context.dataStore.edit {
            it[textKey] = value
        }
    }

    fun load() = context.dataStore.data.map {
        it[textKey].orEmpty()
    }
}