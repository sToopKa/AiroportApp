package com.sto_opka91.airoportapp.data

import com.sto_opka91.airoportapp.database.RoomDao
import com.sto_opka91.airoportapp.models.room.Passenger
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.PassengerReporitory
import javax.inject.Inject

class PassengerRepositoryImpl @Inject constructor(private val roomDao: RoomDao) :
    PassengerReporitory {

    override suspend fun addPassenger(passenger: Passenger): Long {
        return roomDao.insertPassenger(passenger)
    }

    override suspend fun getAllPassengers(): List<Passenger> {
        return roomDao.getAllPassengers()
    }
}