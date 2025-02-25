package com.sto_opka91.airoportapp.data

import com.sto_opka91.airoportapp.database.RoomDao
import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.repository.CardInfoRepository
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(private val roomDao: RoomDao) :
    CardInfoRepository {
    override suspend fun saveCard(number: String, date: String, cvv: String) {
        roomDao.createCardInfo(
            CardInfoEntity(
                id = null,
                idUser = 1,
                numberCard = number,
                dateAction = date,
                cvv = cvv
            )
        )
    }

    override suspend fun getCardForUser(idUser: Int): List<CardInfoEntity> {
        return roomDao.getCards(idUser)
    }
}