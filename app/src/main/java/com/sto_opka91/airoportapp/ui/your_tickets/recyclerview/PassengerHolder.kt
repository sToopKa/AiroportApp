package com.sto_opka91.airoportapp.ui.your_tickets.recyclerview

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.ItemActualTicketBinding
import com.sto_opka91.airoportapp.databinding.PassengerItemBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.models.room.Passenger
import com.sto_opka91.airoportapp.utils.RateFlight
import com.sto_opka91.airoportapp.utils.StatusFlight

class PassengerHolder(
    private val binding: PassengerItemBinding,
    private val onPassengerSelected: (Passenger) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(passenger: Passenger, isSelected: Boolean) {
        binding.tvFioInicial.text = getShortName(passenger.fio)

        // Устанавливаем цвета в зависимости от состояния выбора
        if (isSelected) {
            // Выбранное состояние: синий фон, белый текст
            binding.cvPassenger.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.main_blue))
            binding.tvFioInicial.setTextColor(Color.WHITE)
        } else {
            // Невыбранное состояние: светло-серый фон, темно-серый текст
            binding.cvPassenger.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.item_flight_background))
            binding.tvFioInicial.setTextColor(ContextCompat.getColor(binding.root.context, R.color.color_text_gray))
        }

        binding.root.setOnClickListener {
            onPassengerSelected(passenger)
        }
    }

    private fun getShortName(fullName: String): String {
        val parts = fullName.split(" ")
        if (parts.size >= 3) {
            return "${parts[0]} ${parts[1].first()}.${parts[2].first()}."
        } else if (parts.size == 2) {
            return "${parts[0]} ${parts[1].first()}."
        }
        return fullName
    }
}