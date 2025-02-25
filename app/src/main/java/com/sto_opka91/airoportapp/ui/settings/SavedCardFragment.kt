package com.sto_opka91.airoportapp.ui.settings

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.AlertDialogLayoutBinding
import com.sto_opka91.airoportapp.databinding.FragmentPrivateInfoBinding
import com.sto_opka91.airoportapp.databinding.FragmentSavedCardBinding
import com.sto_opka91.airoportapp.ui.card_action.CardViewModel
import com.sto_opka91.airoportapp.ui.card_action.recylclerView.CardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedCardFragment : Fragment() {

    private var _binding: FragmentSavedCardBinding? = null
    private val binding get() = _binding!!

    private val cardAdapter by lazy { initCardAdapter() }
    private val viewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        initListeners()
        initEvents()
        setupCardValidation()
    }

    private fun setupCardValidation() = with(binding) {
        // Номер карты
        edNumberCard.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                if (s.toString() != current) {
                    val digits = s.toString().replace("\\D".toRegex(), "")
                    if (digits.length <= 16) {
                        val formatted = StringBuilder()
                        for (i in digits.indices) {
                            if (i > 0 && i % 4 == 0) formatted.append(" ")
                            formatted.append(digits[i])
                        }
                        current = formatted.toString()
                        edNumberCard.setText(current)
                        edNumberCard.setSelection(current.length)

                        // Установка background в зависимости от валидности
                        edNumberCard.setBackgroundResource(
                            if (digits.length == 16) R.drawable.text_input_radius_blue
                            else R.drawable.text_input_radius_red
                        )
                    }
                }
                isFormatting = false
            }
        })

        // Дата
        edDateAction.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var current = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true

                if (s.toString() != current) {
                    val digits = s.toString().replace("\\D".toRegex(), "")
                    if (digits.length <= 4) {
                        var formatted = digits
                        if (digits.length >= 2) {
                            formatted = "${digits.substring(0, 2)}.${digits.substring(2)}"
                        }
                        current = formatted
                        edDateAction.setText(current)
                        edDateAction.setSelection(current.length)

                        // Установка background
                        val isValid = formatted.matches("^(0[1-9]|1[0-2])\\.([2-9][0-9])\$".toRegex())
                        edDateAction.setBackgroundResource(
                            if (isValid) R.drawable.text_input_radius_blue
                            else R.drawable.text_input_radius_red
                        )
                    }
                }
                isFormatting = false
            }
        })

        // CVV
        edCVV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val cvv = s.toString().replace("\\D".toRegex(), "")
                edCVV.setBackgroundResource(
                    if (cvv.length == 3) R.drawable.text_input_radius_blue
                    else R.drawable.text_input_radius_red
                )
            }
        })

        // Ограничение ввода
        edNumberCard.filters = arrayOf(InputFilter.LengthFilter(19)) // 16 цифр + 3 пробела
        edDateAction.filters = arrayOf(InputFilter.LengthFilter(5))  // MM.YY
        edCVV.filters = arrayOf(InputFilter.LengthFilter(3))        // 3 цифры
    }

    private fun initEvents() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initCardAdapter() = CardAdapter { card ->
        viewModel.selectCard(card.idUser)
    }

    private fun initRecyclerView() = with(binding) {
        rcSavedCard.layoutManager = LinearLayoutManager(requireContext())
        rcSavedCard.adapter = cardAdapter
    }

    private fun initObservers() {
        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            if (cards.isEmpty()) {
                binding.llExistCard.visibility = View.GONE
                binding.llAddNewCard.visibility = View.VISIBLE

            } else {
                binding.llExistCard.visibility = View.VISIBLE
                binding.llAddNewCard.visibility = View.GONE
                cardAdapter.submitList(cards)

            }
        }
    }

    private fun initListeners() = with(binding) {
        btnReg2.setOnClickListener {
            val cardNumber = edNumberCard.text.toString()
            val dateAction = edDateAction.text.toString()
            val cvv = edCVV.text.toString()

            if (validateInputs(cardNumber, dateAction, cvv)) {
                viewModel.saveCard(cardNumber, dateAction, cvv)
            }
        }

        btnAddNewCard.setOnClickListener {

            llAddNewCard.visibility = View.VISIBLE

            val cardNumber = edNumberCard.text.toString()
            val dateAction = edDateAction.text.toString()
            val cvv = edCVV.text.toString()

            if (validateInputs(cardNumber, dateAction, cvv)) {
                viewModel.saveCard(cardNumber, dateAction, cvv)
            }
        }
    }

    private fun validateInputs(cardNumber: String, dateAction: String, cvv: String): Boolean {
        val cleanCardNumber = cardNumber.replace("\\D".toRegex(), "")
        val cleanDate = dateAction.replace("\\D".toRegex(), "")
        val cleanCvv = cvv.replace("\\D".toRegex(), "")

        val isCardValid = cleanCardNumber.length == 16
        val isDateValid = dateAction.matches("^(0[1-9]|1[0-2])\\.([2-9][0-9])\$".toRegex())
        val isCvvValid = cleanCvv.length == 3

        // Обновляем цвета полей
        binding.tINumberCard.setBoxStrokeColorStateList(
            ColorStateList.valueOf(
                if (isCardValid) ContextCompat.getColor(requireContext(), R.color.main_blue)
                else ContextCompat.getColor(requireContext(), R.color.item_flight_text_red)
            )
        )

        binding.tIDateAction.setBoxStrokeColorStateList(
            ColorStateList.valueOf(
                if (isDateValid) ContextCompat.getColor(requireContext(), R.color.main_blue)
                else ContextCompat.getColor(requireContext(), R.color.item_flight_text_red)
            )
        )

        binding.tICVV.setBoxStrokeColorStateList(
            ColorStateList.valueOf(
                if (isCvvValid) ContextCompat.getColor(requireContext(), R.color.main_blue)
                else ContextCompat.getColor(requireContext(), R.color.item_flight_text_red)
            )
        )

        return isCardValid && isDateValid && isCvvValid
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}