package com.sto_opka91.airoportapp.repository

import com.sto_opka91.airoportapp.models.local.FavoriteClassForUser
import com.sto_opka91.airoportapp.models.room.FavoriteFlightEntity
import com.sto_opka91.airoportapp.utils.Resource

interface FavoriteFlightRepository {
    suspend fun saveFavoriteFlight(id:Int): Resource<Boolean>
    suspend fun deleteFavoriteFlight(id:Int): Resource<Boolean>
    suspend fun getAllFavorite(id:Int): Resource<Boolean>
    suspend fun getAllFavoriteFlight(): Resource<List<FavoriteFlightEntity>>
}