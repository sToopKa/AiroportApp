package com.sto_opka91.airoportapp.ui.settings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.sto_opka91.airoportapp.R
import com.sto_opka91.airoportapp.databinding.AlertDialogLayoutBinding

import com.sto_opka91.airoportapp.databinding.FragmentNewsBinding
import com.sto_opka91.airoportapp.ui.settings.state_holders.SettingsActionList
import com.sto_opka91.airoportapp.utils.ViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsMainFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updatePhoto(it.toString())
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            binding.switchPush.isChecked = true
            viewModel.updatePushNotifications(true)
        } else {
            binding.switchPush.isChecked = false
            Toast.makeText(
                requireContext(),
                "Для работы уведомлений необходимо разрешение",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeViewModel()
        viewModel.loadUserInfo()
    }

    private fun setupClickListeners() {
        binding.ivPhotoUser.setOnClickListener {
            pickImage.launch("image/*")
        }
        binding.ivGoPrivateData.setOnClickListener {
            viewModel.attachToBtn(SettingsActionList.GoToPrivateInfo)
        }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.tvExitFromAcc.setOnClickListener {
            viewModel.onExitClick()
        }

        binding.tvDeleteAcc.setOnClickListener {
            viewModel.onDeleteAccountClick()
        }

        binding.ivGoBankCard.setOnClickListener {
            findNavController().navigate(R.id.action_settingsMainFragment_to_savedCardFragment)
        }

        binding.switchPush.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                safeCheckNotificationPermission {
                    viewModel.updatePushNotifications(true)
                }
            } else {
                viewModel.updatePushNotifications(false)
            }
        }

        binding.ivGoMoney.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке")
        }

        binding.ivGoLanguage.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке")
        }

        binding.ivGoRedactCard.setOnClickListener {
            showInform(requireContext(), "Раздел находится в разработке")
        }
    }

    private fun safeCheckNotificationPermission(onGranted: () -> Unit) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                when {
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        onGranted()
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                        // Показываем объяснение, почему нужны уведомления
                        showPermissionRationaleDialog {
                            try {
                                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } catch (e: Exception) {
                                Log.e("SettingsMainFragment", "Error requesting permission", e)
                                binding.switchPush.isChecked = false
                                Toast.makeText(
                                    requireContext(),
                                    "Ошибка при запросе разрешения",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else -> {
                        try {
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } catch (e: Exception) {
                            Log.e("SettingsMainFragment", "Error requesting permission", e)
                            binding.switchPush.isChecked = false
                            Toast.makeText(
                                requireContext(),
                                "Ошибка при запросе разрешения",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                // На более старых версиях Android разрешение не требуется
                onGranted()
            }
        } catch (e: Exception) {
            Log.e("SettingsMainFragment", "Error checking notification permission", e)
            binding.switchPush.isChecked = false
            Toast.makeText(
                requireContext(),
                "Произошла ошибка при проверке разрешений",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.settingsState.collect { state ->
                if(state.viewType==ViewType.Loaded){
                    state.photoUri?.let { uri ->
                        Glide.with(requireContext())
                            .load(uri)
                            .circleCrop()
                            .placeholder(R.drawable.ic_default_photo)
                            .into(binding.ivPhotoUser)
                    }
                    if(state.fio==""){
                        binding.tvNameUser.text = "Уважаемый пользователь"
                    }else{
                        binding.tvNameUser.text = state.fio
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actions.collect { action ->
                when(action) {
                    is SettingsActionList.UpdatePhoto -> {
                        Snackbar.make(binding.root, "Фото успешно обновлено", Snackbar.LENGTH_SHORT).show()
                    }
                    is SettingsActionList.PhotoPickError -> {
                        Snackbar.make(binding.root, "Ошибка при обновлении фото", Snackbar.LENGTH_SHORT).show()
                    }
                    is SettingsActionList.GoToPrivateInfo ->{
                        findNavController().navigate(R.id.action_settingsMainFragment_to_privateInfoFragment)
                    }
                    is SettingsActionList.GoToLogin -> {
                        findNavController().navigate(R.id.action_settingsMainFragment_to_enterLoginFragment)
                    }
                    is SettingsActionList.ShowError -> {
                        Toast.makeText(requireContext(), action.message, Toast.LENGTH_SHORT).show()
                    }
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


    private fun showPermissionRationaleDialog(onPositiveClick: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Требуется разрешение")
            .setMessage("Для отправки уведомлений необходимо ваше разрешение")
            .setPositiveButton("OK") { _, _ -> onPositiveClick() }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
                binding.switchPush.isChecked = false
            }
            .show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}