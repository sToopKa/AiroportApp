package com.sto_opka91.airoportapp.di

import android.content.Context
import com.sto_opka91.airoportapp.services.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Provides
    @Singleton
    fun provideNotificationRepository(
        @ApplicationContext context: Context
    ): NotificationRepository {
        return NotificationRepository(context)
    }
}