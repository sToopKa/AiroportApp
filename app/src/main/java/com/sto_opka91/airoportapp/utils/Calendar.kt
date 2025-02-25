package com.sto_opka91.airoportapp.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.sto_opka91.airoportapp.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

object Calendar {
    fun showDatePicker(
        fragment: Fragment,
        onDateSelected: (String) -> Unit,
        onReset: () -> Unit
    ) {
        val dialog = Dialog(fragment.requireContext()).apply {
            setContentView(R.layout.custom_calendar_layout)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        val calendarView = dialog.findViewById<CalendarView>(R.id.calendar_view)
        val btnReset = dialog.findViewById<Button>(R.id.btn_reset)
        val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)

        calendarView.maxDate = System.currentTimeMillis()

        var selectedTimeInMillis = calendarView.date

        // Слушатель выбора даты
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = java.util.Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedTimeInMillis = calendar.timeInMillis
        }

        btnReset.setOnClickListener {
            onReset()
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            val calendar = java.util.Calendar.getInstance()
            calendar.timeInMillis = selectedTimeInMillis

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            onDateSelected(dateFormat.format(calendar.time))
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()
    }
}