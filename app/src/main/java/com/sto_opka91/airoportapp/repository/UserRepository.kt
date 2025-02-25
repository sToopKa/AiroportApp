package com.sto_opka91.airoportapp.repository

import com.sto_opka91.airoportapp.models.room.UserInfoEntity
import com.sto_opka91.airoportapp.utils.Resource

interface UserRepository {
    suspend fun saveUser(login: String, password: String): Resource<Boolean>
    suspend fun deleteUser(userId: Int): Resource<Unit>
    suspend fun getRegisterUser(login: String, password: String): Resource<Boolean>
    suspend fun updateUserPhoto(userId: Int, photoUri: String): Resource<Boolean>
    suspend fun getUserPhoto(userId: Int): Resource<String?>

    suspend fun getUserIndo(userId: Int): Resource<UserInfoEntity?>
    suspend fun updateUserInfo(id: Int, field: String, value: String)

}