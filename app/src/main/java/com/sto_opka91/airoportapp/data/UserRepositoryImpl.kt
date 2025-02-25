package com.sto_opka91.airoportapp.data


import android.util.Log
import com.sto_opka91.airoportapp.database.RoomDao
import com.sto_opka91.airoportapp.models.room.UserInfoEntity
import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.utils.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val roomDao: RoomDao): UserRepository {
    override suspend fun saveUser(login: String, password: String): Resource<Boolean> {
        return  try{
            val user = UserInfoEntity(
                null,
                login = login,
                password = password
            )
           roomDao.createUser(user)
                Resource.Success(true)
        }
        catch(e:Exception){
            Resource.Failure(e, false)
        }
    }

  override  suspend fun deleteUser(userId: Int): Resource<Unit> {
        return try {
            roomDao.deleteUserById(userId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getRegisterUser(login: String, password: String): Resource<Boolean> {
      return  try{
            val user = roomDao.getUserByLoginAndPassword(login,password)
          if(user==null)
              Resource.UnSuccess(false )
          else
              Resource.Success(true)
        }
        catch(e:Exception){
            Resource.Failure(e, false)
        }
    }

    override suspend fun updateUserPhoto(userId: Int, photoUri: String): Resource<Boolean> {
        return try {
            roomDao.updateUserPhoto(userId, photoUri)
            Resource.Success(true)
        } catch(e: Exception) {
            Resource.Failure(e, false)
        }
    }

    override suspend fun getUserPhoto(userId: Int): Resource<String?> {
        return try {
            val photo = roomDao.getUserPhoto(userId)
            Resource.Success(photo)
        } catch(e: Exception) {
            Resource.Failure(e, null)
        }
    }


    override suspend fun getUserIndo(userId: Int): Resource<UserInfoEntity?> {
        return try {
            val userInfo = roomDao.getUserInfo(userId)
            Resource.Success(userInfo)
        } catch(e: Exception) {
            Resource.Failure(e, null)
        }
    }

    override suspend fun updateUserInfo(id: Int, field: String, value: String) {
        try {
            when (field) {
                "mail" -> roomDao.updateMail(id, value)
                "fio" -> roomDao.updateFIO(id, value)
                "latinName" -> roomDao.updateLatinName(id, value)
                "telephone" -> roomDao.updateTelephone(id, value)
                "cityDeparture" -> roomDao.updateCityDeparture(id, value)
                "birthDay" -> roomDao.updateBirthDate(id, value)
                "genre" -> roomDao.updateGenre(id, value)
            }
        } catch (e: Exception) {
            Log.e("myLog", "Error updating $field: ${e.message}")
            throw e
        }
    }

}