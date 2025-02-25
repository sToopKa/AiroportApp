package com.sto_opka91.airoportapp.ui.airport_list.StateHolder

import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.utils.ViewType

data class AirplainStateHolders(
    var planeInfoList: List<PlaneInfo> = emptyList(),
    var filterList: List<String> = emptyList(),
    var nameUser: String = "Уважаемый пользователь",
    val photoUri: String? = null,
    var viewType: ViewType = ViewType.Loading
)
