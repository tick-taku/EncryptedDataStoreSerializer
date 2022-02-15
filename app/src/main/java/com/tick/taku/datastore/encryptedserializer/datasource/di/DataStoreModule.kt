package com.tick.taku.datastore.encryptedserializer.datasource.di

import android.content.Context
import com.tick.taku.datastore.encryptedserializer.datasource.TextPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideTextPreferencesDataStore(context: Context): TextPreferencesDataStore {
        return TextPreferencesDataStore(context)
    }

}