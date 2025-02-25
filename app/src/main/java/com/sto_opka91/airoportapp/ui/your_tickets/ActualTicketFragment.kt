package com.sto_opka91.airoportapp.ui.your_tickets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.sto_opka91.airoportapp.databinding.FragmentFinishedTicketBinding
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.repeatOnCreated
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActualTicketFragment : Fragment() {

    private var _binding: FragmentActualTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YourTicketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActualTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setupPassengerToggle()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnAddPassenger.setOnClickListener {
            findNavController().navigate(R.id.action_actualTicketFragment_to_addPassengerFragment)
        }

        binding.btnPereves.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке. Переход на оплату перевеса")
        }

        binding.btnHandBagage.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке. Переход на оплату ручной клади")
        }

    }

    private fun initData() {


        viewModel.viewStateDetail.repeatOnCreated(this){info ->

            binding.tvDatetitle.text = info.planeInfo.date
            binding.tvDepartureCode.text = info.planeInfo.departure_city_code
            binding.tvArriveCode.text = info.planeInfo.arrive_city_code
            binding.tvArriveCode.text = info.planeInfo.arrive_city_code
            binding.tvDepartureDate.text = info.planeInfo.date
            binding.tvArriveDate.text = info.planeInfo.date_arrive
            binding.tvDepartureTime.text = info.planeInfo.time_departure
            binding.tvArriveTime.text = info.planeInfo.time_arrive
            binding.tvNumberFlightInfo.text = info.planeInfo.flight_time
            binding.tvFlightModelInfo.text = info.planeInfo.airplane
            when(info.planeInfo.status_flight){
                StatusFlight.DETAINED -> {
                    binding.tvStatusFlightInfo.text = "Задержан"
                    val color = Color.parseColor("#FA2D2D")
                    binding.tvStatusFlightInfo.setTextColor(color)
                }
                StatusFlight.SHIPPED -> {
                    binding.tvStatusFlightInfo.text = "Отправлен"
                    val color = Color.parseColor("#00D218")
                    binding.tvStatusFlightInfo.setTextColor(color)
                }
                StatusFlight.BOARDING -> {
                    binding.tvStatusFlightInfo.text = "Посадка"
                    val color = Color.parseColor("#006FFD")
                    binding.tvStatusFlightInfo.setTextColor(color)
                }
            }
            when(info.planeInfo.info_status){
                InfoStatus.DANGER -> {
                    val color = Color.parseColor("#FA2D2D")
                    binding.tvlandingInfo.setTextColor(color)
                    binding.tvlandingInfo.text = "Закрыта"
                    binding.tvInfoNotificationTitle.text = "Опоздание"
                    binding.ivNotifications.setImageResource(R.drawable.ic_alert_info)
                    binding.tvInfoNotificationInfo.text = info.planeInfo.info_text
                    binding.cvInfo.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_info_note_alert))

                }
                InfoStatus.FINISH -> {
                    val color = Color.parseColor("#FA2D2D")
                    binding.tvlandingInfo.setTextColor(color)
                    binding.tvlandingInfo.text = "Закрыта"
                    binding.tvInfoNotificationTitle.text = "Посадка завершена"
                    binding.ivNotifications.setImageResource(R.drawable.ic_finish_info)
                    binding.tvInfoNotificationInfo.text = info.planeInfo.info_text
                    binding.cvInfo.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_info_note_finish))
                }
                InfoStatus.INFO -> {
                    val color = Color.parseColor("#00D218")
                    binding.tvlandingInfo.setTextColor(color)
                    binding.tvlandingInfo.text = "Открыта"
                    binding.tvInfoNotificationTitle.text = "Информация"
                    binding.ivNotifications.setImageResource(R.drawable.ic_info)
                    binding.tvInfoNotificationInfo.text = info.planeInfo.info_text
                    binding.cvInfo.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_info_note_info))
                }
            }
            binding.tvDepartureAreaInfo.text = info.planeInfo.departure_area
            binding.tvGateInfo.text = info.planeInfo.gate
            binding.tvReceptionInfo.text = info.planeInfo.reception_desk
        }
    }

    private fun setupPassengerToggle() = with(binding) {
        // Установка начальных значений для первого пассажира
        updatePassengerInfo(
            name = "Иванов Вадим Вадимович",
            classType = "Эконом",
            place = "F62",
            bagage = "Не включен" to Color.RED,
            handBagage = "Оплачено" to Color.parseColor("#006FFD")
        )

        toggleGender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btnFirst -> {
                    updatePassengerInfo(
                        name = "Иванов Вадим Вадимович",
                        classType = "Эконом",
                        place = "F62",
                        bagage = "Не включен" to Color.RED,
                        handBagage = "Оплачено" to Color.parseColor("#006FFD")
                    )
                }
                R.id.btnTwo -> {
                    updatePassengerInfo(
                        name = "Петров Игорь Игоревич",
                        classType = "Первый класс",
                        place = "D62",
                        bagage = "Включен" to Color.parseColor("#006FFD"),
                        handBagage = "Оплачено" to Color.parseColor("#006FFD")
                    )
                }
                R.id.btnTree -> {
                    updatePassengerInfo(
                        name = "Сидоров Семен Семенович",
                        classType = "Бизнес класс",
                        place = "A62",
                        bagage = "Включен" to Color.parseColor("#006FFD"),
                        handBagage = "Не оплачено" to Color.RED
                    )
                }
            }
        }
    }

    private fun updatePassengerInfo(
        name: String,
        classType: String,
        place: String,
        bagage: Pair<String, Int>,
        handBagage: Pair<String, Int>
    ) = with(binding) {
        tvFIOValue.text = name
        tvClassValue.text = classType
        tvPlaceValue.text = place

        tvBagageValue.text = bagage.first
        tvBagageValue.setTextColor(bagage.second)

        tvHandBagageValue.text = handBagage.first
        tvHandBagageValue.setTextColor(handBagage.second)
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