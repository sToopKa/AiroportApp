package com.sto_opka91.airoportapp.ui.favorite_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.FragmentAddCardFragmentBinding
import com.sto_opka91.airoportapp.databinding.FragmentFavoriteFlightBinding
import com.sto_opka91.airoportapp.ui.airport_list.AirportInfoViewModel
import com.sto_opka91.airoportapp.ui.airport_list.StateHolder.AirportListActions
import com.sto_opka91.airoportapp.ui.airport_list.recyclerview.adapter.FlightAdapter
import com.sto_opka91.airoportapp.ui.favorite_fragment.actions.FavoriteActions
import com.sto_opka91.airoportapp.ui.favorite_fragment.recyclerview.FavoriteFlightAdapter
import com.sto_opka91.airoportapp.utils.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFlightFragment : Fragment() {
    private var _binding: FragmentFavoriteFlightBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteFlightViewModel by activityViewModels()
    private val favoriteAdapter by lazy { initFavoriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()
        viewModel.loadFavoritePlanes()
    }

    private fun setupRecyclerView() = with(binding) {
        rcFavoriteFlight.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())

            // Добавляем свайп для удаления
            val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val planeInfo = favoriteAdapter.currentList[position]
                    viewModel.deleteFavoriteFlight(planeInfo)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.favoritePlanes.collect { planes ->
                        favoriteAdapter.submitList(planes) // Показываем/скрываем элементы в зависимости от наличия данных
                        if (planes.isEmpty()) {
                            binding.apply {
                                rcFavoriteFlight.visibility = View.GONE

                            }
                        } else {
                            binding.apply {
                                rcFavoriteFlight.visibility = View.VISIBLE

                            }
                        }
                    }
                }

                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.apply {

                        }
                    }
                }

                launch {
                    viewModel.actions.collect { action ->
                        when (action) {
                            is FavoriteActions.SelectPlaneEvent -> {
                                // Можно добавить дополнительную логику при выборе рейса
                            }
                            is FavoriteActions.NavigateToInfoPlane -> {
                                findNavController().navigate(
                                    R.id.action_favoriteFlightFragment_to_detailInfoPlaneFragment
                                )
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initFavoriteAdapter() = FavoriteFlightAdapter { planeInfo ->
        viewModel.onPlaneClick(FavoriteActions.SelectPlaneEvent(planeInfo))
        viewModel.onPlaneClick(FavoriteActions.NavigateToInfoPlane)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}