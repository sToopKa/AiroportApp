package com.sto_opka91.airoportapp.repository

import com.sto_opka91.airoportapp.models.room.Passenger

interface PassengerReporitory {
    suspend fun addPassenger(passenger: Passenger): Long
    suspend fun getAllPassengers(): List<Passenger>
}