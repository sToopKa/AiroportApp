package com.sto_opka91.airoportapp

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sto_opka91.airoportapp.databinding.ActivityMainBinding
import com.sto_opka91.airoportapp.ui.airport_list.AirportListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isNavigating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        setupBottomNavigation()
        setupNavigationVisibility()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Получаем текущий фрагмент
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment
                val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

                when (currentFragment) {
                    is AirportListFragment -> {
                        // Если мы на главном экране, показываем диалог подтверждения выхода
                        finish()
                    }
                    else -> {
                        // Для остальных фрагментов используем стандартную навигацию назад
                        isEnabled = false
                        navController.popBackStack()
                        isEnabled = true
                    }
                }
            }
        })
    }


    private fun setupBottomNavigation() {
        // Настраиваем внешний вид неактивных элементов
        binding.navView.itemTextColor = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(
                ContextCompat.getColor(this, R.color.color_text_gray),
                ContextCompat.getColor(this, R.color.main_blue)
            )
        )

        // Настраиваем навигацию
        binding.navView.setOnItemSelectedListener { item ->
            if (!isNavigating) {
                isNavigating = true
                when (item.itemId) {
                    R.id.your_tickets -> {
                        if (navController.currentDestination?.id != R.id.yourTicketsFragment) {
                            navController.navigate(R.id.yourTicketsFragment)
                        }
                    }
                    R.id.favorite_flights -> {
                        if (navController.currentDestination?.id != R.id.favoriteFlightFragment) {
                            navController.navigate(R.id.favoriteFlightFragment)
                        }
                    }
                    R.id.profil_info -> {
                        if (navController.currentDestination?.id != R.id.privateInfoFragment) {
                            navController.navigate(R.id.privateInfoFragment)
                        }
                    }
                    R.id.airoport_list -> {
                        if (navController.currentDestination?.id != R.id.airportListFragment) {
                            navController.navigate(R.id.airportListFragment)
                        }
                    }
                }
                isNavigating = false
            }
            true
        }
    }

    private fun setupNavigationVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->

            val hideBottomNavFragments = listOf(
                R.id.enterLoginFragment,
                R.id.promtLoginFragment
            )

            binding.cvBottomMenu.visibility = if (destination.id in hideBottomNavFragments) {
                View.GONE
            } else {
                View.VISIBLE
            }

            // Обновляем выбранный элемент в меню без вызова навигации
            if (!isNavigating) {
                when (destination.id) {
                    R.id.yourTicketsFragment -> binding.navView.selectedItemId = R.id.your_tickets
                    R.id.favoriteFlightFragment -> binding.navView.selectedItemId = R.id.favorite_flights
                    R.id.privateInfoFragment -> binding.navView.selectedItemId = R.id.profil_info
                    R.id.airportListFragment -> binding.navView.selectedItemId = R.id.airoport_list
                }
            }
        }
    }
}