package com.tick.taku.datastore.encryptedserializer.datasource.di

import android.content.Context
import androidx.datastore.core.Serializer
import com.tick.taku.datastore.encryptedserializer.datasource.InputText
import com.tick.taku.datastore.encryptedserializer.datasource.InputTextSerializer
import com.tick.taku.datastore.encryptedserializer.datasource.TextPreferencesDataStore
import com.tick.taku.datastore.encryptedserializer.datasource.TextProtoDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideStringFormat(): StringFormat = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideInputTextSerializer(stringFormat: StringFormat): Serializer<InputText> {
        return InputTextSerializer(stringFormat)
    }

    @Provides
    @Singleton
    fun provideTextPreferencesDataStore(@ApplicationContext context: Context): TextPreferencesDataStore {
        return TextPreferencesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideTextProtoDataStore(
        @ApplicationContext context: Context,
        serializer: Serializer<InputText>
    ): TextProtoDataStore {
        return TextProtoDataStore(context, serializer)
    }
}