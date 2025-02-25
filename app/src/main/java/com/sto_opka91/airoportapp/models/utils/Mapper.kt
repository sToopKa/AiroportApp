package com.sto_opka91.airoportapp.models.utils

import com.sto_opka91.airoportapp.models.local.FavoriteClassForUser
import com.sto_opka91.airoportapp.models.room.FavoriteFlightEntity

fun FavoriteFlightEntity.toFavoriteClassForUser(): FavoriteClassForUser {
    return FavoriteClassForUser(
        idByUser = this.idFlight
    )
}


fun List<FavoriteFlightEntity>.toFavoriteClassList(): List<FavoriteClassForUser> {
    return this.map { it.toFavoriteClassForUser() }
}