package com.sto_opka91.airoportapp.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_info")
data class CardInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val idUser: Int,
    val numberCard: String,
    val dateAction: String,
    val cvv: String
)
