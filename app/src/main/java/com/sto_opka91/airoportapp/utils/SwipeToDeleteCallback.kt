package com.sto_opka91.airoportapp.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.R
import kotlin.math.abs

abstract class SwipeToDeleteCallback(context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_btn)
    private val background = ColorDrawable(Color.WHITE)

    // Разные размеры для ширины и высоты
    private val iconHeightDp = 32
    private val iconWidthDp = 64 // Увеличили ширину
    private val marginInDp = 16

    private val iconHeight = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        iconHeightDp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

    private val iconWidth = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        iconWidthDp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

    private val marginSize = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        marginInDp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        // Центрируем иконку по вертикали
        val iconTop = itemView.top + (itemHeight - iconHeight) / 2
        val iconBottom = iconTop + iconHeight

        val maxSwipeDistance = itemView.width * 0.4f
        val currentSwipeDistance = abs(dX).coerceIn(0f, maxSwipeDistance)
        val swipeRatio = currentSwipeDistance / maxSwipeDistance

        if (dX < 0) { // Свайп влево
            val backgroundLeft = itemView.right + dX.toInt()
            val backgroundRight = itemView.right

            background.setBounds(
                backgroundLeft,
                itemView.top,
                backgroundRight,
                itemView.bottom
            )
            background.draw(c)

            // Позиционируем иконку с новой шириной
            val iconRight = itemView.right - marginSize
            val iconLeft = iconRight - iconWidth

            deleteIcon?.setBounds(
                iconLeft,
                iconTop,
                iconRight,
                iconBottom
            )

            deleteIcon?.alpha = (swipeRatio * 255).toInt()
            deleteIcon?.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    // Увеличиваем порог свайпа
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.4f // 40% от ширины элемента
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 0.5f
    }
}