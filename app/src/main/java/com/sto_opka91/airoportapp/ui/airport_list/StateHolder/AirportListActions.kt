package com.sto_opka91.airoportapp.ui.airport_list.StateHolder

import com.sto_opka91.airoportapp.models.local.PlaneInfo


interface AirportListActions {
    data object NavigateToInfoPlane : AirportListActions
    data object NavigateToSettings : AirportListActions
    data object NavigateToBuyTicketFlight : AirportListActions
    data object NavigateToAddOptionsTicketFlight : AirportListActions

    data class SelectPlaneEvent(val planeInfo: PlaneInfo) : AirportListActions
    data class SelectFilterEvent(val filter: String) : AirportListActions

}