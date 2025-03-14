package com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.ItemBuyTicketFlightLayoutBinding
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.utils.RateFlight
import com.sto_opka91.airoportapp.utils.StatusFlight

class BuyTicketHolder (
    private val binding: ItemBuyTicketFlightLayoutBinding,
    private val onItemNavigateToScrollListener: (PlaneInfo) -> Unit,
    private val onFavoriteClick: (PlaneInfo) -> Unit,
    private val onBuyClick: (PlaneInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: PlaneInfo) {
        bindInfo(task)
    }

    private fun bindInfo(task: PlaneInfo) = with(binding) {

        tvPrice.text = "${task.price}₽"

        // Устанавливаем иконку избранного
        ivFavorite.setImageResource(
            if (task.isFavorite) R.drawable.ic_favorite
            else R.drawable.ic_non_favorite
        )

        ivFavorite.setOnClickListener {
            onFavoriteClick(task)
        }

        btnBuy.setOnClickListener {
            onBuyClick(task)
        }

        tvDeparture.text = task.departure_city
        tvArrival.text = task.arrive_city
        tvDate.text = task.date
        tvCount.text = task.count_passengers.toString()
        when (task.rate_flight) {
            RateFlight.ECONOM -> {
                tvRateFlight.text = "Эконом"
            }

            RateFlight.BISNESS -> {
                tvRateFlight.text = "Бизнес-класс"
            }

            RateFlight.FIRST_CLASS -> {
                tvRateFlight.text = "Эконом"
            }
        }


        when (task.status_flight) {
            StatusFlight.DETAINED -> {
                tvStatus.text = "Задержан"
                val color = Color.parseColor("#FA2D2D")
                tvStatus.setTextColor(color)
            }

            StatusFlight.BOARDING -> {
                tvStatus.text = "Посадка"
                val color = Color.parseColor("#006FFD")
                tvStatus.setTextColor(color)
            }

            StatusFlight.SHIPPED -> {
                tvStatus.text = "Отправлен"
                val color = Color.parseColor("#00D218")
                tvStatus.setTextColor(color)

            }
        }
        tvRoute.text = task.route
        tvAirplane.text = task.airplane
        tvTimeDeparture.text = task.time_departure
        tvTimeArrive.text = task.time_arrive
        tvDateDepartureFull.text = task.date
        tvDateArriveFull.text = task.date_arrive
        tvCityDepartureSmall.text = task.departure_city
        tvCodeCityDepartureSmall.text = task.departure_city_code
        tvCityArriveSmall.text = task.arrive_city
        tvCodeCityArriveSmall.text = task.arrive_city_code

        cvPlaneInfo.setOnClickListener {
            onItemNavigateToScrollListener.invoke(task)
        }

    }
}