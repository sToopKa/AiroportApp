package com.sto_opka91.airoportapp.ui.your_tickets.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemActualTicketBinding
import com.sto_opka91.airoportapp.databinding.ItemFinishedTicketBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo

class FInishedTicketAdapter (
    private val onItemNavigateToScrollListener: (PlaneInfo) -> Unit
) : ListAdapter<PlaneInfo, FinishedTicketHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedTicketHolder {

        val context = parent.context
        return FinishedTicketHolder(
            ItemFinishedTicketBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onItemNavigateToScrollListener
        )
    }

    override fun onBindViewHolder(holder: FinishedTicketHolder, position: Int) {
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