package com.sto_opka91.airoportapp.di

import android.content.Context
import android.content.SharedPreferences
import com.sto_opka91.airoportapp.data.PreferencesManagerImpl
import com.sto_opka91.airoportapp.repository.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(prefs: SharedPreferences): PreferencesManager {
        return PreferencesManagerImpl(prefs)
    }
}