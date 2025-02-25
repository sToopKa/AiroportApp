package com.sto_opka91.airoportapp.ui.favorite_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions
import com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder.DetailInfoStateHolder
import com.sto_opka91.airoportapp.ui.favorite_fragment.actions.FavoriteActions
import com.sto_opka91.airoportapp.utils.LIST_PLANE
import com.sto_opka91.airoportapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFlightViewModel @Inject constructor(
    private val favoriteFlightRepository: FavoriteFlightRepository
) : ViewModel() {

    private val _actions: Channel<FavoriteActions> = Channel(Channel.BUFFERED)
    val actions: Flow<FavoriteActions> = _actions.receiveAsFlow()

    private val _viewStateDetail = MutableStateFlow(DetailInfoStateHolder())
    val viewStateDetail = _viewStateDetail.asStateFlow()

    private val _favoritePlanes = MutableStateFlow<List<PlaneInfo>>(emptyList())
    val favoritePlanes = _favoritePlanes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()





    fun loadFavoritePlanes() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = favoriteFlightRepository.getAllFavoriteFlight()) {
                is Resource.Success -> {
                    result.data?.let { favoriteFlights ->
                        // Получаем список ID избранных рейсов
                        val favoriteIds = favoriteFlights.map { it.idFlight }

                        // Фильтруем LIST_PLANE по полученным ID
                        val favoritePlanes = LIST_PLANE.filter { plane ->
                            favoriteIds.contains(plane.id)
                        }

                        _favoritePlanes.value = favoritePlanes
                    }
                }
                is Resource.Failure -> {
                    // Можно добавить обработку ошибки
                }
                else -> Unit
            }
            _isLoading.value = false
        }
    }

    fun deleteFavoriteFlight(planeInfo: PlaneInfo) {
        viewModelScope.launch {
            when (val result = favoriteFlightRepository.deleteFavoriteFlight(planeInfo.id)) {
                is Resource.Success -> {
                    // После успешного удаления обновляем список
                    loadFavoritePlanes()
                }
                is Resource.Failure -> {
                    // Можно добавить обработку ошибки
                }
                else -> Unit
            }
        }
    }

    fun onPlaneClick(action: FavoriteActions) {
        viewModelScope.launch {
            _actions.send(action)
            when (action) {
                is FavoriteActions.SelectPlaneEvent -> {
                    _viewStateDetail.value = _viewStateDetail.value.copy(
                        planeInfo = action.planeInfo
                    )
                }
                else -> Unit
            }
        }
    }
}