package com.sto_opka91.airoportapp.ui.airport_list.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.databinding.ItemFilterLayoutBinding
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.FilterHolder
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.PlaneInfoViewHolder
import java.util.logging.Filter

class FilterAdapter(
    private val onItemNavigateToScrollListener: (String) -> Unit
) : ListAdapter<String, FilterHolder>(DiffCallback) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        val context = parent.context
        return FilterHolder(
            ItemFilterLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            onItemNavigateToScrollListener
        )
    }

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPosition)

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition

            selectedPosition = if (selectedPosition == holder.adapterPosition) {
                RecyclerView.NO_POSITION
            } else {
                holder.adapterPosition
            }

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)


            if (selectedPosition != RecyclerView.NO_POSITION) {
                onItemNavigateToScrollListener.invoke(getItem(selectedPosition))
            } else {
                onItemNavigateToScrollListener.invoke("")
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}



