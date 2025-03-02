package com.sto_opka91.airoportapp.ui.your_tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.models.room.Passenger
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.PassengerReporitory
import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder.DetailInfoStateHolder
import com.sto_opka91.airoportapp.ui.your_tickets.passengerStateHolder.PassengerStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourTicketViewModel @Inject constructor(
    private val favoriteFlightRepository: FavoriteFlightRepository,
    private val userInfoRepository: UserRepository,
    private val passengerreporitory: PassengerReporitory
) : ViewModel()  {

    private val _viewStateDetail = MutableStateFlow(DetailInfoStateHolder())
    val viewStateDetail = _viewStateDetail.asStateFlow()

    private val _passengerState = MutableStateFlow(PassengerStateHolder())
    val passengerState = _passengerState.asStateFlow()

    fun updateViewState(planeInfo: PlaneInfo){
        viewModelScope.launch {
            _viewStateDetail.update { prev ->
                prev.copy(
                    planeInfo = planeInfo

                    )
            }
        }
    }

    fun loadPassengers() {
        viewModelScope.launch {
            val passengers = passengerreporitory.getAllPassengers()
            _passengerState.update {
                PassengerStateHolder(
                    passengers = passengers,
                    selectedPassenger = passengers.firstOrNull()
                )
            }
        }
    }

    fun selectPassenger(passenger: Passenger) {
        _passengerState.update { state ->
            state.copy(selectedPassenger = passenger)
        }
    }

    fun addPassenger(fio: String, passportNumber: String) {
        if (fio.isBlank() || passportNumber.isBlank()) return

        val newPassenger = Passenger(
            fio = fio,
            passportNumber = passportNumber
        )

        viewModelScope.launch {
            passengerreporitory.addPassenger(newPassenger)
            loadPassengers()
        }
    }
}