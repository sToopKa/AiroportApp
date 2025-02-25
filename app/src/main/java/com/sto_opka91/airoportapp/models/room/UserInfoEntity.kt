package com.sto_opka91.airoportapp.models.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val login: String,
    val password: String,
    val photoUri: String? = null,
    val birthDay: String? = "",
    val mail: String? = "",
    val fio: String? = "",
    val latinName: String? = "",
    val telephone: String? = "",
    val cityDeparture: String? = "",
    val genre: String? = ""
)
