package com.sto_opka91.airoportapp.ui.your_tickets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.AlertDialogLayoutBinding
import com.sto_opka91.airoportapp.databinding.FragmentActualTicketBinding
import com.sto_opka91.airoportapp.databinding.FragmentAddPassengerBinding
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.RateFlight
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.repeatOnCreated
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPassengerFragment : Fragment() {

    private var _binding: FragmentAddPassengerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YourTicketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPassengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextFields()
        initData()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddPassenger.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке. Переход на оплату нового пассажира")
        }
        binding.btnAddPassenger.setOnClickListener {
            val fio = binding.edFIO.text.toString()
            val passportNumber = binding.edPassport.text.toString()

            if (validateInputs(fio, passportNumber)) {
                viewModel.addPassenger(fio, passportNumber)
                showInform(requireContext(), "Пассажир успешно добавлен")
                findNavController().popBackStack()
            }
        }
    }

    private fun validateInputs(fio: String, passportNumber: String): Boolean {
        val fioWords = fio.trim().split("\\s+".toRegex())
        val isFioValid = fioWords.size == 3

        val cleanPassportNumber = passportNumber.replace("[^0-9]".toRegex(), "")
        val isPassportValid = cleanPassportNumber.length == 10

        if (!isFioValid) {
            showInform(requireContext(), "Введите ФИО полностью (Фамилия Имя Отчество)")
            return false
        }

        if (!isPassportValid) {
            showInform(requireContext(), "Номер паспорта должен содержать 10 цифр")
            return false
        }

        return true
    }

    private fun initData() {


        viewModel.viewStateDetail.repeatOnCreated(this){task ->

           binding.tvDeparture.text = task.planeInfo.departure_city
            binding.tvArrival.text = task.planeInfo.arrive_city
            binding.tvDate.text = task.planeInfo.date
            binding.tvCount.text = task.planeInfo.count_passengers.toString()
            when (task.planeInfo.rate_flight) {
                RateFlight.ECONOM -> {
                    binding.tvRateFlight.text = "Эконом"
                }

                RateFlight.BISNESS -> {
                    binding.tvRateFlight.text = "Бизнес-класс"
                }

                RateFlight.FIRST_CLASS -> {
                    binding.tvRateFlight.text = "Эконом"
                }
            }
            when (task.planeInfo.status_flight) {
                StatusFlight.DETAINED -> {
                    binding.tvStatus.text = "Задержан"
                    val color = Color.parseColor("#FA2D2D")
                    binding.tvStatus.setTextColor(color)
                }

                StatusFlight.BOARDING -> {
                    binding.tvStatus.text = "Посадка"
                    val color = Color.parseColor("#006FFD")
                    binding.tvStatus.setTextColor(color)
                }

                StatusFlight.SHIPPED -> {
                    binding.tvStatus.text = "Отправлен"
                    val color = Color.parseColor("#00D218")
                    binding.tvStatus.setTextColor(color)

                }
            }
            binding.tvRoute.text = task.planeInfo.route
            binding.tvAirplane.text = task.planeInfo.airplane
            binding.tvTimeDeparture.text = task.planeInfo.time_departure
            binding.tvTimeArrive.text = task.planeInfo.time_arrive
            binding.tvDateDepartureFull.text = task.planeInfo.date
            binding.tvDateArriveFull.text = task.planeInfo.date_arrive
            binding.tvCityDepartureSmall.text = task.planeInfo.departure_city
            binding.tvCodeCityDepartureSmall.text = task.planeInfo.departure_city_code
            binding.tvCityArriveSmall.text = task.planeInfo.arrive_city
            binding.tvCodeCityArriveSmall.text = task.planeInfo.arrive_city_code
        }
    }

    private fun setupTextFields() = with(binding) {
        // Для ФИО
        edFIO.apply {
            inputType = InputType.TYPE_CLASS_TEXT
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    val words = s.toString().trim().split("\\s+".toRegex())
                    val isValid = words.size == 3
                    edFIO.background = ContextCompat.getDrawable(
                        requireContext(),
                        if (isValid) R.drawable.text_input_radius_blue
                        else R.drawable.text_input_radius_red
                    )
                }
            })
        }

        // Для паспорта
        edPassport.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s == null) return

                    // Удаляем все пробелы
                    val digits = s.toString().replace("\\s".toRegex(), "")

                    // Форматируем текст
                    if (digits.length <= 10) {
                        val formatted = buildString {
                            digits.forEachIndexed { index, char ->
                                if (index == 2 || index == 4) {
                                    append(" ")
                                }
                                append(char)
                            }
                        }

                        // Если текст изменился, обновляем его без рекурсии
                        if (formatted != s.toString()) {
                            setText(formatted)
                            setSelection(formatted.length)
                        }
                    }

                    // Проверяем валидность (10 цифр) и меняем фон
                    val isValid = digits.length == 10
                    edPassport.background = ContextCompat.getDrawable(
                        requireContext(),
                        if (isValid) R.drawable.text_input_radius_blue
                        else R.drawable.text_input_radius_red
                    )
                }
            })
        }
    }

    private fun showInform(context: Context, text: String) {
        val bindingAlert = AlertDialogLayoutBinding.inflate(LayoutInflater.from(context))
        val mDialogBuilder = android.app.AlertDialog.Builder(context)
        mDialogBuilder.setView(bindingAlert.root)

        val alertDialog = mDialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingAlert.TextView1.text = text
        bindingAlert.Image2.setOnClickListener { alertDialog.cancel() }
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}