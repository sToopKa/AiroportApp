package com.sto_opka91.airoportapp.ui.your_tickets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.AlertDialogLayoutBinding
import com.sto_opka91.airoportapp.databinding.FragmentActualTicketBinding
import com.sto_opka91.airoportapp.databinding.FragmentFinishedTicketBinding
import com.sto_opka91.airoportapp.models.room.Passenger
import com.sto_opka91.airoportapp.ui.your_tickets.recyclerview.PassengerAdapter
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.repeatOnCreated
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActualTicketFragment : Fragment() {

    private var _binding: FragmentActualTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YourTicketViewModel by activityViewModels()

    // Объявляем адаптер
    private lateinit var passengerAdapter: PassengerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActualTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализируем адаптер
        setupRecyclerView()

        initData()
        viewModel.loadPassengers() // Загружаем пассажиров
        observePassengers()

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

    private fun setupRecyclerView() {
        passengerAdapter = PassengerAdapter { passenger ->
            viewModel.selectPassenger(passenger)
        }

        binding.rcPassenger.apply {
            adapter = passengerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            // Добавляем отступы между элементами
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    // Добавляем отступ справа для всех элементов, кроме последнего
                    val position = parent.getChildAdapterPosition(view)
                    if (position != parent.adapter?.itemCount?.minus(1)) {
                        outRect.right = resources.getDimensionPixelSize(R.dimen.passenger_item_margin)
                    }
                }
            })
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
        tvPlaceValue.text = "F13"
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

    private fun observePassengers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.passengerState.collect { state ->
                if (state.passengers.isEmpty()) {
                    binding.tvNoPassengers.visibility = View.VISIBLE
                    binding.passengerInfoContainer.visibility = View.GONE
                    binding.rcPassenger.visibility = View.GONE
                } else {
                    // Показываем список пассажиров
                    binding.tvNoPassengers.visibility = View.GONE
                    binding.rcPassenger.visibility = View.VISIBLE
                    binding.passengerInfoContainer.visibility = View.VISIBLE

                    // Обновляем данные в адаптере
                    passengerAdapter.submitList(state.passengers)

                    // Обновляем выбранного пассажира
                    passengerAdapter.updateSelectedPassenger(state.selectedPassenger?.id.toString())

                    // Обновляем информацию о выбранном пассажире
                    state.selectedPassenger?.let { passenger ->
                        updatePassengerInfo(
                            name = passenger.fio,
                            classType = passenger.classType,
                            place = passenger.place,
                            bagage = passenger.baggage to if (passenger.baggage == "Включен") Color.parseColor("#006FFD") else Color.RED,
                            handBagage = passenger.handBaggage to if (passenger.handBaggage == "Оплачено") Color.parseColor("#006FFD") else Color.RED
                        )
                    }
                }
            }
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}