package com.sto_opka91.airoportapp.ui.airport_list.recyclerview.holder


import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.databinding.ItemFilterLayoutBinding


class FilterHolder(
    private val binding: ItemFilterLayoutBinding,
    private val onItemNavigateToScrollListener: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: String, isSelected: Boolean) {
        bindInfo(task, isSelected)
    }

    private fun bindInfo(task: String, isSelected: Boolean) = with(binding) {
        tvFilterName.text = task
        val defaultColorCv = Color.parseColor("#EAF2FF")
        val selectedColorCv = Color.parseColor("#006FFD")
        val selectedTextColor = Color.parseColor("#EAF2FF")
        val defaultTextColor = Color.parseColor("#006FFD")


        cvItemFilter.setCardBackgroundColor(if (isSelected) selectedColorCv else defaultColorCv)
        tvFilterName.setTextColor(if (isSelected) selectedTextColor else defaultTextColor)
        cvItemFilter.setOnClickListener {
            onItemNavigateToScrollListener.invoke(task)
        }
    }
}