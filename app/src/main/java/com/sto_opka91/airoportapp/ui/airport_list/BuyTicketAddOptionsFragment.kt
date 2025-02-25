package com.sto_opka91.airoportapp.ui.airport_list

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.FragmentUyTicketAddOptionsBinding
import com.sto_opka91.airoportapp.utils.HandBagage
import com.sto_opka91.airoportapp.utils.InfoStatus
import com.sto_opka91.airoportapp.utils.StatusFlight
import com.sto_opka91.airoportapp.utils.repeatOnCreated


class BuyTicketAddOptionsFragment : Fragment() {

    private var _binding: FragmentUyTicketAddOptionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AirportInfoViewModel by activityViewModels()

    private var currentPrice = 0
    private val BAGAGE_23_PRICE = 2990
    private val BAGAGE_2X23_PRICE = 8980
    private val ANIMAL_PRICE = 2990
    private val HAND_BAGAGE_PRICE = 1000
    private val PEREVES_PRICE = 1000
    private val INSURANCE_PRICE = 1000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUyTicketAddOptionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initSwitches()
        spannableText()
        initEvents()
    }

    fun spannableText()=with(binding){
        val spannableBagage23 = SpannableString("2990 Р")
        spannableBagage23.setSpan(
            StrikethroughSpan(),
            0,
            spannableBagage23.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val spannableBagage2x23 = SpannableString("11120 Р")
        spannableBagage2x23.setSpan(
            StrikethroughSpan(),
            0,
            spannableBagage2x23.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        tvBagage23kgRed.text = spannableBagage23
        tvAnimalPriceRed.text = spannableBagage23
        tvBagage2x23kgRed.text = spannableBagage2x23

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
            binding.tvPrice.text  = info.planeInfo.price.toString()+ " Р"
            binding.tvPriceTicketPrice.text  = info.planeInfo.price.toString()+ " Р"
            currentPrice = info.planeInfo.price
            binding.tvItogItogPrice.text = "$currentPrice Р"


            binding.switchHandBagage.isChecked = true
            binding.switchHandBagage.isEnabled = false

            when(info.planeInfo.hand_bagage) {
                HandBagage.YES -> {
                    binding.switchAddBagage.isChecked = true
                    binding.switchAddBagage.isEnabled = false
                }
                HandBagage.NO -> {
                    binding.switchAddBagage.isChecked = false
                    binding.switchAddBagage.isEnabled = false
                }
            }
        }
    }

    private fun initSwitches() = with(binding) {
        // Багаж 23кг
        switchAddBagage23.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchAddBagage23x2.isChecked = false
                llBagage.visibility = View.VISIBLE
                currentPrice += BAGAGE_23_PRICE
            } else {
                llBagage.visibility = View.GONE
                currentPrice -= BAGAGE_23_PRICE
            }
            updateTotalPrice()
        }

        // Багаж 2x23кг
        switchAddBagage23x2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchAddBagage23.isChecked = false
                ll2xBagage.visibility = View.VISIBLE
                currentPrice += BAGAGE_2X23_PRICE
            } else {
                ll2xBagage.visibility = View.GONE
                currentPrice -= BAGAGE_2X23_PRICE
            }
            updateTotalPrice()
        }

        // Животные
        switchAddAnimal.setOnCheckedChangeListener { _, isChecked ->
            llTakeAnimal.visibility = if (isChecked) {
                currentPrice += ANIMAL_PRICE
                View.VISIBLE
            } else {
                currentPrice -= ANIMAL_PRICE
                View.GONE
            }
            updateTotalPrice()
        }

        // Перевес
        switchPereves.setOnCheckedChangeListener { _, isChecked ->
            llPereves.visibility = if (isChecked) {
                currentPrice += PEREVES_PRICE
                View.VISIBLE
            } else {
                currentPrice -= PEREVES_PRICE
                View.GONE
            }
            updateTotalPrice()
        }

        // Страховка
        switchInsurance.setOnCheckedChangeListener { _, isChecked ->
            llInsurance.visibility = if (isChecked) {
                currentPrice += INSURANCE_PRICE
                View.VISIBLE
            } else {
                currentPrice -= INSURANCE_PRICE
                View.GONE
            }
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        binding.tvItogItogPrice.text = "$currentPrice Р"
    }
    private fun initEvents(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnBuyTicket.setOnClickListener {
            findNavController().navigate(R.id.action_buyTicketAddOptionsFragment_to_addCardFragmentFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}