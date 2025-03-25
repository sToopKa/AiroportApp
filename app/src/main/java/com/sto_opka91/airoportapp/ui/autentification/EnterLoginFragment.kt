package com.sto_opka91.airoportapp.ui.autentification

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.AlertDialogLayoutBinding

import com.sto_opka91.airoportapp.databinding.FragmentEnterLoginBinding

import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateActions
import com.sto_opka91.airoportapp.utils.isEmailValid

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EnterLoginFragment : Fragment() {


    private var _binding: FragmentEnterLoginBinding? = null
    private val viewModel: AuthorisationViewModel by viewModels()

    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEnterLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToViewModel()
        initLogic()
    }



    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actions.collect { action ->
                when (action) {
                    is StateActions.NavigateToMainEvent -> {
                        findNavController().navigate(R.id.action_enterLoginFragment_to_airportListFragment)
                    }
                    is StateActions.ShowErrorEvent ->{
                        showInform(requireContext(), action.text)
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
            val email = binding.edLoginIn.text.toString()
            if (isEmailValid(email)) {
                viewModel.checkuser()
            } else {
                showInform(requireContext(), "Введите корректный email")
            }

        }

        tvReg.setOnClickListener {
            findNavController().navigate(R.id.action_enterLoginFragment_to_promtLoginFragment)
        }

        hintRemover(tILLogin,edLoginIn, "Логин")
        hintRemover(tILPass,edPassIn, "Пароль")
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