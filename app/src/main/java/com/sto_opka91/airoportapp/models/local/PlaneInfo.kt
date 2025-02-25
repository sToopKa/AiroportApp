package com.sto_opka91.airoportapp.models.local

import com.sto_opka91.airoportapp.utils.HandBagage
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.RateFlight
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.TransferCount


data class PlaneInfo(
    val id: Int,
    val departure_city: String,
    val departure_city_code: String,
    val arrive_city: String,
    val arrive_city_code: String,
    val flight_time: String,
    val date: String,
    val count_passengers: Int,
    val rate_flight: RateFlight,
    val status_flight: StatusFlight,
    val route: String,
    val airplane: String,
    val time_arrive: String,
    val date_arrive: String,
    val time_departure: String,
    val transfer_count:TransferCount,
    val hand_bagage: HandBagage,
    val reception_desk: String,
    val gate: String,
    val departure_area: String,
    val info_status: InfoStatus,
    val info_text: String,
    val price: Int,
    var isFavorite: Boolean = false
)
