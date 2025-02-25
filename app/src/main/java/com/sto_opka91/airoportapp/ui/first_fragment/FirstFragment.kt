package com.sto_opka91.airoportapp.ui.first_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sto_opka91.airoportapp.ui.autentification.AuthorisationViewModel
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.FragmentFirstBinding
import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateActions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!
    private val viewModel: AuthorisationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewModel()
    }


    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actions.collect { action ->
                when (action) {
                    is StateActions.NavigateToMainEvent -> {

                        findNavController().navigate(R.id.action_firstFragment_to_airportListFragment)
                    }
                    is StateActions.NavigateToLoginFragmentEvent -> {

                        findNavController().navigate(R.id.action_firstFragment_to_enterLoginFragment)
                    }
                    else -> Unit
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}