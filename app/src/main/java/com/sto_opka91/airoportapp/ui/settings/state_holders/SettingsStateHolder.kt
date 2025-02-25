package com.sto_opka91.airoportapp.ui.settings.state_holders

import com.sto_opka91.airoportapp.utils.ViewState
import com.sto_opka91.airoportapp.utils.ViewType

data class SettingsStateHolder(
    val name: String = "Иван Иванов",
    val photoUri: String? = null,
    val birthDate: String? = null,
    val mail: String? = null,
    val fio: String? = null,
    val latinName: String? = null,
    val telephone: String? = null,
    val cityDeparture: String? = null,
    val genre: String? = "male",
    override val viewType: ViewType = ViewType.Loading
):ViewState
