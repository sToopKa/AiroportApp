package com.sto_opka91.airoportapp.data

import android.util.Log
import com.sto_opka91.airoportapp.database.RoomDao
import com.sto_opka91.airoportapp.models.local.FavoriteClassForUser
import com.sto_opka91.airoportapp.models.room.FavoriteFlightEntity
import com.sto_opka91.airoportapp.models.room.UserInfoEntity
import com.sto_opka91.airoportapp.models.utils.toFavoriteClassList
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.utils.Resource
import javax.inject.Inject

class FavoriteFlightRepositoryImpl @Inject constructor(private val roomDao: RoomDao) : FavoriteFlightRepository{

    override suspend fun saveFavoriteFlight(id: Int): Resource<Boolean> {
        return  try{
            val flight = FavoriteFlightEntity(
                null,
                idFlight = id,
                idUser = 1
            )
            roomDao.createFavoriteFlight(flight)

            Resource.Success(true)
        }
        catch(e:Exception){
            Resource.Failure(e, false)
        }
    }

    override suspend fun deleteFavoriteFlight(id: Int): Resource<Boolean> {
        return  try{

           val i = roomDao.deleteFavoriteFlightById(id,1)
            Log.d("myLog", "удалилось:"+i.toString())
            Resource.Success(true)
        }
        catch(e:Exception){
            Resource.Failure(e, false)
        }
    }

    override suspend fun getAllFavorite(id: Int): Resource<Boolean> {
        return  try{
            val favoriteFlights = roomDao.selectFavoriteFlightById(id, 1).toFavoriteClassList()

            if (favoriteFlights.isEmpty()){
                Resource.Success(false)
            }else{
                Resource.Success(true)
            }
        }
        catch(e:Exception){
            Resource.Failure(e, null)
        }
    }

    override suspend fun getAllFavoriteFlight(): Resource<List<FavoriteFlightEntity>> {
        return try {
            val flights = roomDao.selectAllFavoriteFlight()
            Resource.Success(flights)
        } catch (e: Exception) {
            Resource.Failure(e, null)
        }
    }
}