package com.sto_opka91.airoportapp.repository

import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.utils.Resource

interface CardInfoRepository {
    suspend fun saveCard(number: String, date: String, cvv: String)
    suspend fun getCardForUser(idUser: Int): List<CardInfoEntity>
}