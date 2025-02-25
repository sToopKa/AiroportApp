package com.sto_opka91.airoportapp.ui.autentification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sto_opka91.airoportapp.R

import com.sto_opka91.airoportapp.databinding.FragmentPromtLoginBinding
import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateActions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PromtLoginFragment : Fragment() {

    private var _binding: FragmentPromtLoginBinding? = null
    private val viewModel: AuthorisationViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPromtLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModel()
        initLogic()
        viewModel.clearValue()
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actions.collect { action ->
                when (action) {
                    is StateActions.NavigateToMainEvent -> {
                        findNavController().navigate(R.id.action_promtLoginFragment_to_airportListFragment)
                    }
                    is StateActions.ShowErrorEvent -> {
                        // Добавьте обработку ошибки, например показ Toast
                        Toast.makeText(requireContext(), action.text, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }


    private fun initLogic()=with(binding) {
        edLoginIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.onChangedLogin(StateActions.ChangeLoginEvent(p0.toString()))
            }
        })

        edPassIn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.onChangedLogin(StateActions.ChangePasswordEvent(p0.toString()))
            }
        })

        btnReg.setOnClickListener{
            viewModel.regUser()
        }
        ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_promtLoginFragment_to_enterLoginFragment)
        }

        hintRemover(tILLogin,edLoginIn, "Придумайте логин")
        hintRemover(tILPass,edPassIn, "Придумайте пароль")
    }

    private fun hintRemover(textInputLayout: TextInputLayout, textInputEditText: TextInputEditText, text: String) {
        textInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textInputLayout.hint = null
            }
            else {
                if(textInputEditText.text.toString() ==""){
                    textInputLayout.hint = text
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}