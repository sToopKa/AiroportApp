package com.sto_opka91.airoportapp.ui.your_tickets.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemActualTicketBinding
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.favorite_fragment.recyclerview.FavoriteFlightHolder

class ActualTicketAdapter (
    private val onItemNavigateToScrollListener: (PlaneInfo) -> Unit
) : ListAdapter<PlaneInfo, ActualTicketHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActualTicketHolder {

        val context = parent.context
        return ActualTicketHolder(
            ItemActualTicketBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onItemNavigateToScrollListener
        )
    }

    override fun onBindViewHolder(holder: ActualTicketHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlaneInfo>() {
        override fun areItemsTheSame(oldItem: PlaneInfo, newItem: PlaneInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlaneInfo, newItem: PlaneInfo): Boolean {
            return oldItem == newItem
        }
    }
}