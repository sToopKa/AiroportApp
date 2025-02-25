package com.sto_opka91.airoportapp.repository

interface PreferencesManager {
    suspend fun getIsLogin(): Boolean
    suspend fun saveIsLogin(isLogin: Boolean)
}