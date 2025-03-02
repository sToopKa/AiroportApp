package com.sto_opka91.airoportapp.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.repository.PreferencesManager

import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.services.NotificationRepository
import com.sto_opka91.airoportapp.ui.settings.state_holders.SettingsActionList
import com.sto_opka91.airoportapp.ui.settings.state_holders.SettingsStateHolder
import com.sto_opka91.airoportapp.utils.Resource
import com.sto_opka91.airoportapp.utils.ViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val prefs: PreferencesManager,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsStateHolder())
    val settingsState = _settingsState.asStateFlow()

    private val _actions = Channel<SettingsActionList>()
    val actions = _actions.receiveAsFlow()


    init {
        loadUserInfo()
    }

    fun attachToBtn(action: SettingsActionList){
        viewModelScope.launch {
            when (action) {
                SettingsActionList.GoToPrivateInfo -> _actions.send(SettingsActionList.GoToPrivateInfo)
                SettingsActionList.UpdateBirthDate -> _actions.send(SettingsActionList.UpdateBirthDate)
            }
        }
    }

    fun onExitClick() {
        viewModelScope.launch {
            prefs.saveIsLogin(false)
            _actions.send(SettingsActionList.GoToLogin)
        }
    }

    fun onDeleteAccountClick() {
        viewModelScope.launch {
            when (val result = userRepository.deleteUser(1)) { // предполагаем, что ID = 1
                is Resource.Success -> {
                    prefs.saveIsLogin(false)
                    _actions.send(SettingsActionList.GoToLogin)
                }
                is Resource.Failure -> {
                    _actions.send(SettingsActionList.ShowError("Ошибка удаления аккаунта"))
                }

                is Resource.UnSuccess -> TODO()
            }
        }
    }

    fun updatePhoto(uri: String) {
        viewModelScope.launch {
            when(val result = userRepository.updateUserPhoto(1, uri)) {
                is Resource.Success -> {
                    _settingsState.update { it.copy(photoUri = uri) }
                    _actions.send(SettingsActionList.UpdatePhoto(uri))
                }
                is Resource.Failure -> {
                    _actions.send(SettingsActionList.PhotoPickError)
                }
                else -> Unit
            }
        }
    }

    fun loadUserInfo() {
        viewModelScope.launch {
            when(val result = userRepository.getUserIndo(1)) {
                is Resource.Success -> {
                    result.data?.let { info ->
                        _settingsState.update {
                            SettingsStateHolder(
                                mail = info.mail ?: "",
                                fio = info.fio ?: "",
                                latinName = info.latinName ?: "",
                                telephone = info.telephone ?: "",
                                cityDeparture = info.cityDeparture ?: "",
                                birthDate = info.birthDay ?: "",
                                photoUri = info.photoUri,
                                genre = info.genre ?: "man",
                                password = info.password ?: "",
                                viewType = ViewType.Loaded
                            )
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    fun updatePushNotifications(enabled: Boolean) {
        if (enabled) {
            notificationRepository.scheduleNotification()
        } else {
            notificationRepository.cancelNotification()
        }
    }


    fun updateField(field: String, value: String) {
        viewModelScope.launch {
            try {
                userRepository.updateUserInfo(1, field, value)
            } catch (e: Exception) {
                // Обработка ошибок
                Log.e("myLog", "Error updating $field: ${e.message}")
            }
        }
    }

    fun updatePassword(firstPassword: String, secondPassword: String): Boolean {
        if (firstPassword != secondPassword) {
            return false
        }

        viewModelScope.launch {
            try {
                userRepository.updateUserInfo(1, "password", firstPassword)
                _settingsState.update { state ->
                    state.copy(password = firstPassword)
                }
            } catch (e: Exception) {
                Log.e("myLog", "Error updating password: ${e.message}")
            }
        }
        return true
    }

}