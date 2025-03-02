package com.sto_opka91.airoportapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.models.room.FavoriteFlightEntity
import com.sto_opka91.airoportapp.models.room.Passenger
import com.sto_opka91.airoportapp.models.room.UserInfoEntity

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassenger(passenger: Passenger): Long

    @Query("SELECT * FROM passengers")
    suspend fun getAllPassengers(): List<Passenger>

    @Query("DELETE FROM user_info WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("SELECT * FROM user_info WHERE login = :login AND password = :password LIMIT 1")
    suspend fun getUserByLoginAndPassword(login: String, password: String): UserInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createFavoriteFlight(flight: FavoriteFlightEntity)

    @Query("DELETE  FROM favorite_flight WHERE idFlight = :id AND idUser = :idUser")
    suspend fun deleteFavoriteFlightById(id: Int, idUser: Int):Int

    @Query("SELECT * FROM favorite_flight WHERE idFlight = :id AND idUser = :idUser")
    suspend fun selectFavoriteFlightById(id: Int, idUser: Int): List<FavoriteFlightEntity>

    @Query("UPDATE user_info SET photoUri = :photoUri WHERE id = :userId")
    suspend fun updateUserPhoto(userId: Int, photoUri: String)

    @Query("SELECT photoUri FROM user_info WHERE id = :userId")
    suspend fun getUserPhoto(userId: Int): String?

    @Query("UPDATE user_info SET birthDay = :birthday WHERE id = :userId")
    suspend fun updateBirthDate(userId: Int, birthday: String)

    @Query("SELECT birthDay FROM user_info WHERE id = :userId")
    suspend fun getBirthDate(userId: Int): String?

    @Query("UPDATE user_info SET mail = :mail WHERE id = :userId")
    suspend fun updateMail(userId: Int, mail: String)

    @Query("SELECT mail FROM user_info WHERE id = :userId")
    suspend fun getMail(userId: Int): String?

    @Query("UPDATE user_info SET fio = :fio WHERE id = :userId")
    suspend fun updateFIO(userId: Int, fio: String)

    @Query("SELECT fio FROM user_info WHERE id = :userId")
    suspend fun getFIO(userId: Int): String?

    @Query("UPDATE user_info SET latinName = :latinName WHERE id = :userId")
    suspend fun updateLatinName(userId: Int, latinName: String)

    @Query("SELECT latinName FROM user_info WHERE id = :userId")
    suspend fun getLatinName(userId: Int): String?

    @Query("UPDATE user_info SET telephone = :telephone WHERE id = :userId")
    suspend fun updateTelephone(userId: Int, telephone: String)

    @Query("SELECT telephone FROM user_info WHERE id = :userId")
    suspend fun getTelephone(userId: Int): String?

    @Query("UPDATE user_info SET cityDeparture = :cityDeparture WHERE id = :userId")
    suspend fun updateCityDeparture(userId: Int, cityDeparture: String)

    @Query("SELECT cityDeparture FROM user_info WHERE id = :userId")
    suspend fun getCityDeparture(userId: Int): String?

    @Query("SELECT * FROM user_info WHERE id = :userId")
    suspend fun getUserInfo(userId: Int): UserInfoEntity?

    @Query("UPDATE user_info SET genre = :genre WHERE id = :id")
    suspend fun updateGenre(id: Int, genre: String)

    @Query("UPDATE user_info SET password = :password WHERE id = :id")
    suspend fun updatePassword(id: Int, password: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCardInfo(card: CardInfoEntity)

    @Query("SELECT * FROM card_info WHERE idUser = :idUser")
    suspend fun getCards(idUser: Int):  List<CardInfoEntity>

    @Query("SELECT * FROM favorite_flight")
    suspend fun selectAllFavoriteFlight(): List<FavoriteFlightEntity>

}