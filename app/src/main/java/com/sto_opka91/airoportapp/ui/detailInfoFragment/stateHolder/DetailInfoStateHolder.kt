package com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder

import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.utils.HandBagage
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.RateFlight
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.TransferCount

data class DetailInfoStateHolder(
    var planeInfo: PlaneInfo = PlaneInfo(13, "Ярославль", "IAR", "Москва", "SVO", "2ч", "13 октября", 130, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5013", "Airbus A310", "19:30", "13 сентября среда", "18:00", TransferCount.ONE, HandBagage.NO, "29-31", "A13", "N", InfoStatus.INFO, "Просьба пройти на посадку через выход A13", 16100),

)
