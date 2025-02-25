package com.sto_opka91.airoportapp.ui.card_action.recylclerView

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.ItemCardLayoutBinding
import com.sto_opka91.airoportapp.databinding.ItemFlightLayoutBinding
import com.sto_opka91.airoportapp.models.local.PlaneInfo
import com.sto_opka91.airoportapp.models.room.CardInfoEntity
import com.sto_opka91.airoportapp.utils.StatusFlight

class CardHolder(
    private val binding: ItemCardLayoutBinding,
    private val onItemNavigateToScrollListener: (CardInfoEntity) -> Unit,
    private val onCardSelected: (CardHolder) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(card: CardInfoEntity, isSelected: Boolean) = with(binding) {
        // Случайная картинка банка
        val bankImages = listOf(
            R.drawable.ic_bank_1,
            R.drawable.ic_bank_2,
            R.drawable.ic_bank_3
        )
        ivNotifications.setImageResource(bankImages.random())

        val lastFourDigits = card.numberCard.takeLast(4)
        tvInformationCard.text = "VISA $lastFourDigits"

        // Устанавливаем состояние карты
        updateCardState(isSelected)

        root.setOnClickListener {
            onItemNavigateToScrollListener(card)
            onCardSelected(this@CardHolder)
        }
    }

    fun updateCardState(isSelected: Boolean) = with(binding) {
        root.setCardBackgroundColor(
            ContextCompat.getColor(
                root.context,
                if (isSelected) R.color.main_blue else R.color.item_filter_backgroud_unselected
            )
        )
        tvInformationCard.setTextColor(
            if (isSelected) Color.WHITE else Color.BLACK
        )
        btnCloseNote.setImageResource(
            if (isSelected) R.drawable.ic_arrow_forward_white
            else R.drawable.ic_arrow_card
        )
    }
}