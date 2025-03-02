package com.sto_opka91.airoportapp.ui.your_tickets.passengerStateHolder

import com.sto_opka91.airoportapp.models.room.Passenger

data class PassengerStateHolder(
    val passengers: List<Passenger> = emptyList(),
    val selectedPassenger: Passenger? = null,
    val isLoading: Boolean = false
)
