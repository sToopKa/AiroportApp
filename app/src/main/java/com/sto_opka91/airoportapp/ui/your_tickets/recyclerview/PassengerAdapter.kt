package com.sto_opka91.airoportapp.ui.your_tickets.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemFinishedTicketBinding
import com.sto_opka91.airoportapp.databinding.PassengerItemBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.models.room.Passenger

class PassengerAdapter(
    private val onPassengerSelected: (Passenger) -> Unit
) : ListAdapter<Passenger, PassengerHolder>(DiffCallback) {

    private var selectedPassengerId: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerHolder {
        val binding = PassengerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PassengerHolder(binding) { passenger ->

            selectedPassengerId = passenger.id.toString()

            notifyDataSetChanged()

            onPassengerSelected(passenger)
        }
    }

    override fun onBindViewHolder(holder: PassengerHolder, position: Int) {
        val passenger = getItem(position)
        val isSelected = passenger.id.toString() == selectedPassengerId
        holder.bind(passenger, isSelected)
    }

    fun updateSelectedPassenger(passengerId: String?) {
        if (selectedPassengerId != passengerId) {
            selectedPassengerId = passengerId
            notifyDataSetChanged()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Passenger>() {
        override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
            return oldItem == newItem
        }
    }
}