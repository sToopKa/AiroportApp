package com.sto_opka91.airoportapp.ui.your_tickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.FragmentAirportListBinding
import com.sto_opka91.airoportapp.databinding.FragmentYourTicketsBinding
import com.sto_opka91.airoportapp.ui.airport_list.AirportInfoViewModel
import com.sto_opka91.airoportapp.ui.your_tickets.recyclerview.ActualTicketAdapter
import com.sto_opka91.airoportapp.ui.your_tickets.recyclerview.FInishedTicketAdapter
import com.sto_opka91.airoportapp.utils.LIST_PLANE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class YourTicketsFragment : Fragment() {
    private var _binding: FragmentYourTicketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YourTicketViewModel by activityViewModels()

    private val actualAdapter by lazy { initActualAdapter() }
    private val finishedAdapter by lazy { initFinishedAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYourTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setupData()
        setupListeners()
    }

    private fun setupRecyclerViews() = with(binding) {
        // Настройка актуальных билетов
        rcActualTicket.apply {
            adapter = actualAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

        // Настройка завершенных билетов
        rcFinishedTicket.apply {
            adapter = finishedAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun setupData() {

        val actualTickets = LIST_PLANE.take(2)
        actualAdapter.submitList(actualTickets)

        // Берем следующие два элемента для завершенных билетов
        val finishedTickets = LIST_PLANE.drop(2).take(2)
        finishedAdapter.submitList(finishedTickets)
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {

        }


    }

    private fun initActualAdapter() = ActualTicketAdapter { planeInfo ->
       viewModel.updateViewState(planeInfo)
        findNavController().navigate(R.id.action_yourTicketsFragment_to_actualTicketFragment)

    }

    private fun initFinishedAdapter() = FInishedTicketAdapter { planeInfo ->
        viewModel.updateViewState(planeInfo)
        findNavController().navigate(R.id.action_yourTicketsFragment_to_finishedTicketFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}