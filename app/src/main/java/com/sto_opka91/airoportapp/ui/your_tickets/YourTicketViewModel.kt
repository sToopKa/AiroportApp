package com.sto_opka91.airoportapp.ui.your_tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder.DetailInfoStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourTicketViewModel @Inject constructor(
    private val favoriteFlightRepository: FavoriteFlightRepository,
    private val userInfoRepository: UserRepository
) : ViewModel()  {

    private val _viewStateDetail = MutableStateFlow(DetailInfoStateHolder())
    val viewStateDetail = _viewStateDetail.asStateFlow()

    fun updateViewState(planeInfo: PlaneInfo){
        viewModelScope.launch {
            _viewStateDetail.update { prev ->
                prev.copy(
                    planeInfo = planeInfo

                    )
            }
        }
    }
}