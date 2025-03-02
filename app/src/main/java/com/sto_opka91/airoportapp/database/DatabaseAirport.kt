package com.sto_opka91.airoportapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.models.room.FavoriteFlightEntity
import com.sto_opka91.airoportapp.models.room.Passenger
import com.sto_opka91.airoportapp.models.room.UserInfoEntity

@Database(entities = [UserInfoEntity::class, FavoriteFlightEntity::class, CardInfoEntity::class, Passenger::class], version =5, exportSchema = false)
abstract class DatabaseAirport: RoomDatabase() {
    abstract fun roomDao(): RoomDao
}