package com.sto_opka91.airoportapp.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.bottomNavigationStyle
) : BottomNavigationView(context, attrs, defStyleAttr) {

    private var _selectedItemId = -1

    override fun setSelectedItemId(itemId: Int) {
        _selectedItemId = itemId
        super.setSelectedItemId(itemId)
    }

    override fun getSelectedItemId(): Int {
        return _selectedItemId
    }

    fun clearSelection() {
        try {
            _selectedItemId = -1

            // Временно отключаем группу элементов меню
            menu.setGroupCheckable(0, false, false)

            // Снимаем выделение со всех элементов меню
            for (i in 0 until menu.size()) {
                menu.getItem(i).isChecked = false
            }

            // Снова включаем группу элементов меню
            menu.setGroupCheckable(0, true, true)
        } catch (e: Exception) {
            Log.e("myLog", "Error clearing selection", e)
        }
    }
}