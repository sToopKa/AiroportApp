package com.sto_opka91.airoportapp.ui.favorite_fragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.PlaneInfoViewHolder

class FavoriteFlightAdapter (
    private val onItemNavigateToScrollListener: (PlaneInfo) -> Unit
) : ListAdapter<PlaneInfo, FavoriteFlightHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFlightHolder {

        val context = parent.context
        return FavoriteFlightHolder(
            ItemFlightLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onItemNavigateToScrollListener
        )
    }

    override fun onBindViewHolder(holder: FavoriteFlightHolder, position: Int) {
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