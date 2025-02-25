package com.sto_opka91.airoportapp.ui.autentification


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.repository.PreferencesManager
import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateActions
import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateHolders
import com.sto_opka91.airoportapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorisationViewModel @Inject constructor(
    private val prefs: PreferencesManager,
    private val roomRepo: UserRepository
) : ViewModel() {

    init{

            viewModelScope.launch {
                _viewState.update { prev ->

                    prev.copy(
                        isLogin = prefs.getIsLogin()
                    )
                }
                if(_viewState.value.isLogin){
                    _actions.send(StateActions.NavigateToMainEvent)
                }else{
                    _actions.send(StateActions.NavigateToLoginFragmentEvent)
                }
            }
    }

    private var login_ = mutableStateOf("")
    private var password_ = mutableStateOf("")

    private val _actions: Channel<StateActions> = Channel(Channel.BUFFERED)
    val actions: Flow<StateActions> = _actions.receiveAsFlow()

    private val _viewState = MutableStateFlow(StateHolders())
    val viewState = _viewState.asStateFlow()

    fun onChangedLogin(eventChange: StateActions){
        when(eventChange){
            is StateActions.ChangeLoginEvent -> login_.value = eventChange.login
            is StateActions.ChangePasswordEvent -> password_.value = eventChange.password
            else -> Unit
        }
    }

    fun clearValue(){
        login_.value = ""
        password_.value = ""
    }



    fun checkuser(){
        viewModelScope.launch {
           when(roomRepo.getRegisterUser(login_.value, password_.value)) {
               is Resource.Success ->{
                   _actions.send(StateActions.NavigateToMainEvent)
                   prefs.saveIsLogin(true)
               }
               else -> _actions.send(StateActions.ShowErrorEvent("Нет пользователя"))
           }

        }
    }

    fun regUser() {
        viewModelScope.launch {
            if (login_.value.isBlank() || password_.value.isBlank()) {
                _actions.send(StateActions.ShowErrorEvent("Введите логин и пароль"))
                return@launch
            }

            when(roomRepo.saveUser(login_.value, password_.value)) {
                is Resource.Success -> {
                    prefs.saveIsLogin(true)
                    _actions.send(StateActions.NavigateToMainEvent)
                }
                else -> _actions.send(StateActions.ShowErrorEvent("Не получилось зарегистрироваться"))
            }
        }
    }

}