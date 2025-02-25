package com.sto_opka91.airoportapp.ui.airport_list.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemBuyTicketFlightLayoutBinding
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.BuyTicketHolder
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.PlaneInfoViewHolder

class BuyTicketAdapter (
    private val onItemNavigateToScrollListener: (PlaneInfo) -> Unit,
    private val onFavoriteClick: (PlaneInfo) -> Unit,
    private val onBuyClick: (PlaneInfo) -> Unit
) : ListAdapter<PlaneInfo, BuyTicketHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyTicketHolder {
        val context = parent.context
        return BuyTicketHolder(
            ItemBuyTicketFlightLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onItemNavigateToScrollListener,
            onFavoriteClick,
            onBuyClick
        )
    }

    override fun onBindViewHolder(holder: BuyTicketHolder, position: Int) {
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