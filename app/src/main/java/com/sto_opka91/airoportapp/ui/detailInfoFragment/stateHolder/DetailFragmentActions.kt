package com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder

import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions

interface DetailFragmentActions {
    data object CloseNotifications : DetailFragmentActions
    data object SwitchOn : DetailFragmentActions
    data object SwitchOff : DetailFragmentActions
    data class ErrorAction(val error: String) : DetailFragmentActions
}