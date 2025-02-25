package com.sto_opka91.airoportapp.ui.airport_list

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sto_opka91.airoportapp.R

import com.sto_opka91.airoportapp.databinding.FragmentAirportListBinding
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.adapter.FilterAdapter
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.adapter.FlightAdapter
import com.sto_opka91.airoportapp.ui.autentification.AuthorisationViewModel
import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateActions
import com.sto_opka91.airoportapp.utils.ViewType
import com.sto_opka91.airoportapp.utils.repeatOnCreated
import com.swnishan.materialdatetimepicker.datepicker.MaterialDatePickerDialog
import com.swnishan.materialdatetimepicker.datepicker.MaterialDatePickerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AirportListFragment : Fragment() {

    private var _binding: FragmentAirportListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AirportInfoViewModel by activityViewModels()



    private val adapterPlane by lazy { initPlaneAdapter() }
    private val adapterFilter by lazy { initFilterAdapter() }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAirportListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadInfo()
        initRecyclerView()

        subscribeToViewModel()
        initEvents()
    }

    private fun initEvents() {
        binding.ivSettings.setOnClickListener { viewModel.onPlaneClick(AirportListActions.NavigateToSettings)
        }
        binding.btnShow.setOnClickListener { viewModel.onPlaneClick(AirportListActions.NavigateToBuyTicketFlight)
        }
        binding.ivReverseTablo.setOnClickListener {
            val departureCityText = binding.edCityDeparture.text.toString()
            val arriveCityText = binding.edCityArrive.text.toString()

            binding.edCityDeparture.setText(arriveCityText)
            binding.edCityArrive.setText(departureCityText)
        }


        binding.edDepartureDate.setOnClickListener {
            showDatePicker(
                fragment = this,
                onDateSelected = { date ->
                    binding.edDepartureDate.setText(date)
                },
                onReset = {
                    binding.edDepartureDate.setText("")
                }
            )
        }


        binding.edArriveDate.setOnClickListener {
            showDatePicker(
                fragment = this,
                onDateSelected = { date ->
                    binding.edArriveDate.setText(date)
                },
                onReset = {
                    binding.edArriveDate.setText("")
                }
            )
        }
    }
    fun showDatePicker(
        fragment: Fragment,
        onDateSelected: (String) -> Unit,
        onReset: () -> Unit
    ) {
        val dialog = MaterialDatePickerDialog.Builder
            .setTitle("Выберите дату")
            .setNegativeButtonText("Отмена")
            .setPositiveButtonText("Готово")
            .setDate(System.currentTimeMillis())
            .setDateFormat(MaterialDatePickerView.DateFormat.DD_MMMM_YYYY)
            .setFadeAnimation(350L, 1050L, 0.3f, 0.7f)
            .build()

        dialog.setOnDatePickListener(object : MaterialDatePickerView.OnDatePickedListener {
            override fun onDatePicked(date: Long) {
                val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale("ru")).format(Date(date))
                onDateSelected(formattedDate)
            }
        })

        dialog.show(fragment.childFragmentManager, "DATE_PICKER")
    }


    private fun initPlaneAdapter(): FlightAdapter =
        FlightAdapter(
            onItemNavigateToScrollListener = { planeInfo ->
                viewModel.onPlaneClick(AirportListActions.SelectPlaneEvent(planeInfo))
                viewModel.onPlaneClick(AirportListActions.NavigateToInfoPlane)
            }
        )

    private fun initFilterAdapter(): FilterAdapter =
        FilterAdapter (
            onItemNavigateToScrollListener = {
                viewModel.onFilterClick(AirportListActions.SelectFilterEvent(it))
            }
        )

    private fun initRecyclerView() = with(binding) {
        rcPlane.layoutManager = LinearLayoutManager(requireContext())
        rcPlane.adapter = adapterPlane
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rcFilter.layoutManager = layoutManager
        rcFilter.adapter = adapterFilter

        // Добавляем слушатель прокрутки
        rcPlane.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isHidden = false
            private var lastDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Сохраняем направление скролла
                if (dy != 0) lastDy = dy

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Проверяем только позицию первого элемента
                val shouldHide = firstVisibleItemPosition > 0

                if (shouldHide != isHidden) {
                    isHidden = shouldHide
                    // Анимируем только если изменилось состояние видимости
                    llTopData.animate()
                        .alpha(if (shouldHide) 0f else 1f)
                        .setDuration(150)
                        .withEndAction {
                            llTopData.visibility = if (shouldHide) View.GONE else View.VISIBLE
                        }
                        .start()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Когда скролл остановился, проверяем нужно ли показать/скрыть элементы
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (firstVisibleItemPosition == 0 && lastDy < 0) {
                        // Показываем элементы, если скроллим вверх и первый элемент виден
                        llTopData.visibility = View.VISIBLE
                        llTopData.animate()
                            .alpha(1f)
                            .setDuration(150)
                            .start()
                        isHidden = false
                    }
                }
            }
        })
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actions.collect { action ->
                when (action) {
                    is AirportListActions.NavigateToInfoPlane -> {
                        findNavController().navigate(R.id.action_airportListFragment_to_detailInfoPlaneFragment)
                    }
                    is AirportListActions.NavigateToSettings -> {
                        findNavController().navigate(R.id.action_airportListFragment_to_settingsMainFragment)
                    }
                    is AirportListActions.NavigateToBuyTicketFlight -> {
                        findNavController().navigate(R.id.action_airportListFragment_to_buyTicketAiroportFragment)
                    }

                    else -> Unit
                }
            }
        }
        viewModel.viewState.repeatOnCreated(this) {
            if(it.viewType==ViewType.Loaded){
                adapterPlane.submitList(it.planeInfoList)
                adapterFilter.submitList(it.filterList)
                if(it.nameUser==""){
                    binding.tvNameHeader.text = "Уважаемый пользователь"
                }else{
                    binding.tvNameHeader.text = it.nameUser
                }

                it.photoUri?.let { uri ->
                    Glide.with(requireContext())
                        .load(uri)
                        .circleCrop()
                        .placeholder(R.drawable.ic_default_photo)
                        .into(binding.ivHeaderPhoto)
                }
            }



        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}