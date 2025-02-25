package com.sto_opka91.airoportapp.ui.settings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.AlertDialogLayoutBinding
import com.sto_opka91.airoportapp.databinding.FragmentNewsBinding
import com.sto_opka91.airoportapp.databinding.FragmentPrivateInfoBinding
import com.sto_opka91.airoportapp.ui.autentification.StateHolder.StateActions
import com.sto_opka91.airoportapp.ui.settings.state_holders.SettingsActionList
import com.sto_opka91.airoportapp.utils.ViewType
import com.swnishan.materialdatetimepicker.datepicker.MaterialDatePickerDialog
import com.swnishan.materialdatetimepicker.datepicker.MaterialDatePickerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale



@AndroidEntryPoint
class PrivateInfoFragment : Fragment() {

    private var _binding: FragmentPrivateInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updatePhoto(it.toString())
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivateInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextWatchers()
        observeViewModel()
        initDataPicker()
        initGenreSelection()
        viewModel.loadUserInfo()
    }

    private fun initDataPicker() {
        binding.edBirthDay.setOnClickListener {
            viewModel.attachToBtn(SettingsActionList.UpdateBirthDate)
        }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.ivPhotoUser.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.tvExitFromAcc.setOnClickListener {
            viewModel.onExitClick()
        }

        binding.tvDeleteAcc.setOnClickListener {
            viewModel.onDeleteAccountClick()
        }
        binding.ivTgPrivate.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке.")
        }

        binding.ivApplePrivate.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке.")
        }

        binding.ivGooglePrivate.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке.")
        }

        binding.ivVkPrivate.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке.")
        }
    }

    private fun initGenreSelection() {
        binding.toggleGender.setOnCheckedChangeListener { group, checkedId ->

                val genre = when (checkedId) {
                    R.id.btnMale -> "male"
                    R.id.btnFemale -> "female"
                    else -> ""
                }
                viewModel.updateField("genre", genre)

        }
    }



    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.settingsState.collect { state ->
                if(state.viewType == ViewType.Loaded){
                    try {
                        state.photoUri?.let { uri ->
                            Glide.with(requireContext())
                                .load(uri)
                                .circleCrop()
                                .placeholder(R.drawable.ic_default_photo)
                                .into(binding.ivPhotoUser)
                        }
                        binding.apply {
                            edMail.setText(state.mail)
                            edFIO.setText(state.fio)
                            edLatinName.setText(state.latinName)
                            edTelephone.setText(state.telephone)
                            edCityDeparture.setText(state.cityDeparture)
                            edBirthDay.setText(state.birthDate)

                            when (state.genre) {
                                "male" -> toggleGender.check(R.id.btnMale)
                                "female" -> toggleGender.check(R.id.btnFemale)
                            }
                        }
                    } finally {

                    }
                }


            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actions.collect { action ->
                when(action) {
                    is SettingsActionList.UpdateBirthDate -> {
                        showDatePicker(
                            fragment = this@PrivateInfoFragment,
                            onDateSelected = { date ->
                                binding.edBirthDay.setText(date)
                                viewModel.updateField("birthDay", date)
                            },
                            onReset = {
                                binding.edBirthDay.setText("")
                            }
                        )
                    }
                    is SettingsActionList.GoToLogin -> {
                        findNavController().navigate(R.id.action_privateInfoFragment_to_enterLoginFragment)
                    }
                    is SettingsActionList.ShowError -> {
                        Toast.makeText(requireContext(), action.message, Toast.LENGTH_SHORT).show()
                    }
                    is SettingsActionList.UpdatePhoto -> {
                        Snackbar.make(binding.root, "Фото успешно обновлено", Snackbar.LENGTH_SHORT).show()
                    }
                    is SettingsActionList.PhotoPickError -> {
                        Snackbar.make(binding.root, "Ошибка при обновлении фото", Snackbar.LENGTH_SHORT).show()
                    }

                }
            }
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

    private fun initTextWatchers() {
        binding.edFIO.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                viewModel.updateField("fio", text)

                val words = text.trim().split("\\s+".toRegex())
                val isValid = words.size == 3
                binding.edFIO.background = ContextCompat.getDrawable(
                    requireContext(),
                    if (isValid) R.drawable.text_input_radius_blue
                    else R.drawable.text_input_radius_red
                )
            }
        })

        binding.edMail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                viewModel.updateField("mail", text)

                val isValid = text.contains("@") && text.contains(".")
                binding.edMail.background = ContextCompat.getDrawable(
                    requireContext(),
                    if (isValid) R.drawable.text_input_radius_blue
                    else R.drawable.text_input_radius_red
                )
            }
        })

        binding.edLatinName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                viewModel.updateField("latinName", text)

                val isValid = text.matches(Regex("^[a-zA-Z\\s]*$")) && text.isNotEmpty()
                binding.edLatinName.background = ContextCompat.getDrawable(
                    requireContext(),
                    if (isValid) R.drawable.text_input_radius_blue
                    else R.drawable.text_input_radius_red
                )
            }
        })

        binding.edTelephone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s == null) return

                // Удаляем все нецифровые символы
                val digits = s.toString().replace(Regex("[^0-9]"), "")

                // Ограничиваем ввод 11 цифрами
                if (digits.length > 11) {
                    val truncated = digits.substring(0, 11)
                    formatAndSetPhoneNumber(truncated)
                    return
                }

                formatAndSetPhoneNumber(digits)

                // Проверяем валидность (11 цифр)
                val isValid = digits.length == 11
                binding.edTelephone.background = ContextCompat.getDrawable(
                    requireContext(),
                    if (isValid) R.drawable.text_input_radius_blue
                    else R.drawable.text_input_radius_red
                )

                viewModel.updateField("telephone", digits)
            }

            private fun formatAndSetPhoneNumber(digits: String) {
                // Форматируем номер телефона
                val formatted = when {
                    digits.isEmpty() -> ""
                    digits.length == 1 -> "+$digits"
                    digits.length <= 4 -> "+${digits[0]} (${digits.substring(1)}"
                    digits.length <= 7 -> "+${digits[0]} (${digits.substring(1, 4)}) ${digits.substring(4)}"
                    digits.length <= 9 -> "+${digits[0]} (${digits.substring(1, 4)}) ${digits.substring(4, 7)} ${digits.substring(7)}"
                    else -> "+${digits[0]} (${digits.substring(1, 4)}) ${digits.substring(4, 7)} ${digits.substring(7, 9)} ${digits.substring(9)}"
                }

                // Обновляем текст без рекурсии
                if (formatted != binding.edTelephone.text.toString()) {
                    binding.edTelephone.removeTextChangedListener(this)
                    binding.edTelephone.setText(formatted)
                    binding.edTelephone.setSelection(formatted.length)
                    binding.edTelephone.addTextChangedListener(this)
                }
            }
        })
        binding.edCityDeparture.addTextChangedListener(createTextWatcher("cityDeparture"))
    }

    private fun createTextWatcher(field: String): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {

                    viewModel.updateField(field, s?.toString() ?: "")

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