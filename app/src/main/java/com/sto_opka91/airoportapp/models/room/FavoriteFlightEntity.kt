package com.sto_opka91.airoportapp.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_flight")
data class FavoriteFlightEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val idFlight: Int,
    val idUser: Int
)
