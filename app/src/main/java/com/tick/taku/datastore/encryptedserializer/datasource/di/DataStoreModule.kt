package com.tick.taku.datastore.encryptedserializer.datasource.di

import android.content.Context
import com.tick.taku.datastore.encryptedserializer.datasource.TextPreferencesDataStore
import com.tick.taku.datastore.encryptedserializer.datasource.TextProtoDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideTextPreferencesDataStore(@ApplicationContext context: Context): TextPreferencesDataStore {
        return TextPreferencesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideTextProtoDataStore(@ApplicationContext context: Context): TextProtoDataStore {
        return TextProtoDataStore(context)
    }
}