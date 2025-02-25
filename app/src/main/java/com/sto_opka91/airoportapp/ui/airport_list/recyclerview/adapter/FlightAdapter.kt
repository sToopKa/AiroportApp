package com.sto_opka91.airoportapp.ui.airport_list.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.PlaneInfoViewHolder

class FlightAdapter(
    private val onItemNavigateToScrollListener: (PlaneInfo) -> Unit
) : ListAdapter<PlaneInfo, PlaneInfoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaneInfoViewHolder {

        val context = parent.context
        return PlaneInfoViewHolder(
            ItemFlightLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onItemNavigateToScrollListener
        )
    }

    override fun onBindViewHolder(holder: PlaneInfoViewHolder, position: Int) {
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
