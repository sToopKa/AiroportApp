package com.sto_opka91.airoportapp.ui.card_action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.repository.CardInfoRepository
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardInfoRepository: CardInfoRepository
) : ViewModel() {

    private val _cards = MutableLiveData<List<CardInfoEntity>>()
    val cards: LiveData<List<CardInfoEntity>> = _cards

    private val _selectedCardId = MutableLiveData<Int>()
    val selectedCardId: LiveData<Int> = _selectedCardId

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            _cards.value = cardInfoRepository.getCardForUser(1)
        }
    }

    fun saveCard(number: String, date: String, cvv: String) {
        viewModelScope.launch {
            cardInfoRepository.saveCard(number, date, cvv)
            loadCards()
        }
    }

    fun selectCard(cardId: Int) {
        _selectedCardId.value = cardId
    }

}