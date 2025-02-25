package com.sto_opka91.airoportapp.ui.favorite_fragment.actions

import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions

interface FavoriteActions {

    data object NavigateToInfoPlane : FavoriteActions

    data class SelectPlaneEvent(val planeInfo: PlaneInfo) : FavoriteActions
}