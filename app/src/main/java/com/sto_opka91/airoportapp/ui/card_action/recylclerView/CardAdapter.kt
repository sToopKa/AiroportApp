package com.sto_opka91.airoportapp.ui.card_action.recylclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sto_opka91.airoportapp.databinding.ItemCardLayoutBinding
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder.PlaneInfoViewHolder

class CardAdapter(
    private val onItemNavigateToScrollListener: (CardInfoEntity) -> Unit
) : ListAdapter<CardInfoEntity, CardHolder>(DiffCallback) {

    private var selectedHolder: CardHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(
            ItemCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemNavigateToScrollListener,
            ::onCardSelected
        )
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.bind(
            getItem(position),
            holder == selectedHolder
        )
    }

    private fun onCardSelected(newHolder: CardHolder) {

        if (selectedHolder == newHolder) {
            selectedHolder?.updateCardState(false)
            selectedHolder = null
            return
        }

        selectedHolder?.updateCardState(false)


        selectedHolder = newHolder
        newHolder.updateCardState(true)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CardInfoEntity>() {
        override fun areItemsTheSame(oldItem: CardInfoEntity, newItem: CardInfoEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CardInfoEntity, newItem: CardInfoEntity): Boolean {
            return oldItem == newItem
        }
    }
}