package com.sto_opka91.airoportapp.ui.airport_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.repository.FavoriteFlightRepository
import com.sto_opka91.airoportapp.repository.UserRepository
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirplainStateHolders
import com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder.DetailFragmentActions
import com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder.DetailInfoStateHolder
import com.sto_opka91.airoportapp.ui.settings.state_holders.SettingsStateHolder
import com.sto_opka91.airoportapp.utils.HandBagage
import com.sto_opka91.airoportapp.utils.LIST_DETAILED_INFO
import com.sto_opka91.airoportapp.utils.LIST_PLANE
import com.sto_opka91.airoportapp.utils.RateFlight
import com.sto_opka91.airoportapp.utils.Resource

import com.sto_opka91.airoportapp.utils.TransferCount
import com.sto_opka91.airoportapp.utils.ViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportInfoViewModel @Inject constructor(
    private val favoriteFlightRepository: FavoriteFlightRepository,
    private val userInfoRepository: UserRepository
) : ViewModel() {


    private val _actions: Channel<AirportListActions> = Channel(Channel.BUFFERED)
    val actions: Flow<AirportListActions> = _actions.receiveAsFlow()

    private val _actionsDetailFragment: Channel<DetailFragmentActions> = Channel(Channel.BUFFERED)
    val actionsDetailFragment: Flow<DetailFragmentActions> = _actionsDetailFragment.receiveAsFlow()

    private val _viewState = MutableStateFlow(AirplainStateHolders())
    val viewState = _viewState.asStateFlow()

    private val _viewStateDetail = MutableStateFlow(DetailInfoStateHolder())
    val viewStateDetail = _viewStateDetail.asStateFlow()




    private var selectedFilter: String? = null

    init {

        _viewState.update { prev ->
            prev.copy(
                planeInfoList = LIST_PLANE,
                filterList = LIST_DETAILED_INFO
            )
        }
    }



    fun loadInfo() {
        viewModelScope.launch {

            val userDeferred = async { userInfoRepository.getUserIndo(1) }
            val favoritesDeferred = async { favoriteFlightRepository.getAllFavoriteFlight() }

            val userResult = userDeferred.await()
            val favoritesResult = favoritesDeferred.await()

            when (userResult) {
                is Resource.Success -> {
                    userResult.data?.let { info ->
                        val planeList = LIST_PLANE.toMutableList()

                        // Получаем set избранных ID рейсов
                        val favoriteFlightIds = when (favoritesResult) {
                            is Resource.Success -> {
                                favoritesResult.data
                                    .filter { it.idUser == 1 }
                                    .map { it.idFlight }
                                    .toSet()
                            }
                            else -> emptySet()
                        }


                        val updatedPlaneList = planeList.map { plane ->
                            plane.copy(isFavorite = favoriteFlightIds.contains(plane.id))
                        }

                        _viewState.update {
                            AirplainStateHolders(
                                nameUser = info.fio ?: "Уважаемый пользователь",
                                planeInfoList = updatedPlaneList,
                                filterList = LIST_DETAILED_INFO,
                                photoUri = info.photoUri,
                                viewType = ViewType.Loaded
                            )
                        }
                    }
                }
                else -> {
                    Log.d("myLog", "getUserInfo failed")
                    Unit
                }
            }
        }
    }


    fun onPlaneClick(eventChange: AirportListActions){
        viewModelScope.launch {
            when(eventChange){
                is AirportListActions.SelectPlaneEvent -> {
                    _viewStateDetail.update { prev ->
                        prev.copy(
                            planeInfo = eventChange.planeInfo,

                        )
                    }
                }
                is AirportListActions.NavigateToInfoPlane -> _actions.send(AirportListActions.NavigateToInfoPlane)
                is AirportListActions.NavigateToSettings -> _actions.send(AirportListActions.NavigateToSettings)
                is AirportListActions.NavigateToBuyTicketFlight -> _actions.send(AirportListActions.NavigateToBuyTicketFlight)
                is AirportListActions.NavigateToAddOptionsTicketFlight -> _actions.send(AirportListActions.NavigateToAddOptionsTicketFlight)

                else -> Unit
            }
        }
    }

    fun onDetailFragmentClick(eventChange: DetailFragmentActions){
        viewModelScope.launch {
            when(eventChange){
                is DetailFragmentActions.CloseNotifications -> {
                    _actionsDetailFragment.send(DetailFragmentActions.CloseNotifications)
                }

                else -> Unit
            }
        }
    }

    fun onFilterClick(eventChange: AirportListActions) {
        viewModelScope.launch {
            when (eventChange) {
                is AirportListActions.SelectFilterEvent -> {
                    selectedFilter = eventChange.filter
                    applyFilter()
                }
                else -> Unit
            }
        }
    }

    private fun applyFilter() {
        val filteredList = when (selectedFilter) {
            "прямой" -> LIST_PLANE.filter { it.transfer_count == TransferCount.ONE }
            "с пересадкой" -> LIST_PLANE.filter { it.transfer_count == TransferCount.TWO }
            "бизнес" -> LIST_PLANE.filter { it.rate_flight == RateFlight.BISNESS }
            "эконом" -> LIST_PLANE.filter { it.rate_flight == RateFlight.ECONOM }
            "ручная кладь" -> LIST_PLANE.filter { it.hand_bagage == HandBagage.YES }
            else -> LIST_PLANE
        }

        _viewState.update { prev ->
            prev.copy(
                planeInfoList = filteredList
            )
        }
    }

    fun addToFavorite(){
        viewModelScope.launch {
            when (val result = favoriteFlightRepository.saveFavoriteFlight(_viewStateDetail.value.planeInfo.id)) {
                is Resource.Success  -> Unit
                else -> { _actionsDetailFragment.send(DetailFragmentActions.ErrorAction("Ошибка сохранения")) }
            }
        }
    }

    fun deleteFromFavorite(){
        viewModelScope.launch {
            when (val result = favoriteFlightRepository.deleteFavoriteFlight(_viewStateDetail.value.planeInfo.id)) {
                is Resource.Success  -> Unit
                else -> { _actionsDetailFragment.send(DetailFragmentActions.ErrorAction("Ошибка удаления")) }
            }
        }
    }

    fun checkFromFavorite(){
        viewModelScope.launch {
            when (val result = favoriteFlightRepository.getAllFavorite(_viewStateDetail.value.planeInfo.id)) {
                is Resource.Success  -> {
                    if(result.data){
                        _actionsDetailFragment.send(DetailFragmentActions.SwitchOn)
                    }else{
                        _actionsDetailFragment.send(DetailFragmentActions.SwitchOff)
                    }
                }

                else -> {
                    _actionsDetailFragment.send(DetailFragmentActions.ErrorAction("Ошибка удаления")) }
            }
        }
    }

    fun toggleFavorite(planeInfo: PlaneInfo) {
        viewModelScope.launch {
            if (planeInfo.isFavorite) {
                when (val result = favoriteFlightRepository.deleteFavoriteFlight(planeInfo.id)) {
                    is Resource.Success -> {
                        updatePlaneInfoFavoriteStatus(planeInfo.id, false)
                    }
                    else -> {
                        _actionsDetailFragment.send(DetailFragmentActions.ErrorAction("Ошибка удаления из избранного"))
                    }
                }
            } else {
                when (val result = favoriteFlightRepository.saveFavoriteFlight(planeInfo.id)) {
                    is Resource.Success -> {
                        updatePlaneInfoFavoriteStatus(planeInfo.id, true)
                    }
                    else -> {
                        _actionsDetailFragment.send(DetailFragmentActions.ErrorAction("Ошибка добавления в избранное"))
                    }
                }
            }
        }
    }

    private fun updatePlaneInfoFavoriteStatus(id: Int, isFavorite: Boolean) {
        val currentList = _viewState.value.planeInfoList.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(isFavorite = isFavorite)
            _viewState.update { it.copy(planeInfoList = currentList) }
        }
    }




}