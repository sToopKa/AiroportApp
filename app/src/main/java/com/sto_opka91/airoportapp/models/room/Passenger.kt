package com.sto_opka91.airoportapp.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passengers")
data class Passenger(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fio: String,
    val passportNumber: String,
    val classType: String = "Эконом",
    val place: String = "",
    val baggage: String = "Не включен",
    val handBaggage: String = "Не оплачено"
)
