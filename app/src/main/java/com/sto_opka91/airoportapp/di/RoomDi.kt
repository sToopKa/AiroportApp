package com.sto_opka91.airoportapp.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.sto_opka91.airoportapp.data.CardRepositoryImpl
import com.sto_opka91.airoportapp.data.FavoriteFlightRepositoryImpl
import com.sto_opka91.airoportapp.data.PassengerRepositoryImpl
import com.sto_opka91.airoportapp.data.UserRepositoryImpl
import com.sto_opka91.airoportapp.database.DatabaseAirport
import com.sto_opka91.airoportapp.database.RoomDao
import com.sto_opka91.airoportapp.repository.CardInfoRepository
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.PassengerReporitory
import com.sto_opka91.airoportapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDi {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DatabaseAirport {
        return Room.databaseBuilder(context, DatabaseAirport::class.java, "database_airport")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRoomDao(database: DatabaseAirport): RoomDao {
        return database.roomDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(roomDao: RoomDao, firebaseAuth: FirebaseAuth): UserRepository {
        return UserRepositoryImpl(roomDao, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFavoriteFlightRepository(roomDao: RoomDao): FavoriteFlightRepository {
        return FavoriteFlightRepositoryImpl(roomDao)
    }

    @Provides
    @Singleton
    fun provideCardInfoRepository(roomDao: RoomDao): CardInfoRepository {
        return CardRepositoryImpl(roomDao)
    }

    @Provides
    @Singleton
    fun providePassengerRepository(roomDao: RoomDao): PassengerReporitory {
        return PassengerRepositoryImpl(roomDao)
    }

}