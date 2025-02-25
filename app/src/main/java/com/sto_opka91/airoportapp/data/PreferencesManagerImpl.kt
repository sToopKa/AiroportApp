package com.sto_opka91.airoportapp.data

import android.content.SharedPreferences
import com.sto_opka91.airoportapp.repository.PreferencesManager
import javax.inject.Inject

private enum class MetadataKeys {
    IS_LOGIN
}

class PreferencesManagerImpl @Inject constructor(
    private val prefs: SharedPreferences,
): PreferencesManager {



    override suspend fun getIsLogin(): Boolean = prefs.getBoolean(MetadataKeys.IS_LOGIN.name, false)
    override suspend fun saveIsLogin(isLogin: Boolean) = saveToPreferences(isLogin, MetadataKeys.IS_LOGIN)



    private fun <T> saveToPreferences(value: T?, key: MetadataKeys) {
        val editor: SharedPreferences.Editor = prefs.edit()
        when (value) {
            is Int -> editor.putInt(key.name, value)
            is String -> editor.putString(key.name, value)
            is Boolean -> editor.putBoolean(key.name, value)
        }
        editor.apply()
    }
}