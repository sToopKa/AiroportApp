package com.sto_opka91.airoportapp.ui.detailInfoFragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.FragmentMapBinding
import com.sto_opka91.airoportapp.ui.airport_list.AirportInfoViewModel
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions
import com.sto_opka91.airoportapp.ui.detailInfoFragment.stateHolder.DetailFragmentActions
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.repeatOnCreated
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailInfoPlaneFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val viewModel: AirportInfoViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initEvents()
        subscribeToViewModel()
    }

    private fun initData() {
        viewModel.checkFromFavorite()

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

    private fun initEvents(){

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.btnCloseNote.setOnClickListener {
            viewModel.onDetailFragmentClick(DetailFragmentActions.CloseNotifications)
        }

        binding.switchFavorite.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.addToFavorite()
                Log.d("myLog", "ischecekd")
            } else {
                viewModel.deleteFromFavorite()
            }
        }
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actionsDetailFragment.collect { action ->
                when (action) {
                    is DetailFragmentActions.CloseNotifications -> {
                        binding.cvInfo.visibility = View.GONE
                    }
                    is DetailFragmentActions.SwitchOn -> {
                        binding.switchFavorite.isChecked = true
                    }
                    is DetailFragmentActions.SwitchOff -> {
                        binding.switchFavorite.isChecked = false
                    }
                    is DetailFragmentActions.ErrorAction -> {
                        Snackbar.make(binding.root, action.error, Snackbar.LENGTH_SHORT).show()
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