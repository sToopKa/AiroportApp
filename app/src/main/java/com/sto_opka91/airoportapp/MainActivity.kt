package com.sto_opka91.airoportapp

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.navigation.NavigationBarView
import com.sto_opka91.airoportapp.databinding.ActivityMainBinding
import com.sto_opka91.airoportapp.ui.airport_list.AirportListFragment
import com.sto_opka91.airoportapp.utils.CustomBottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isNavigating = false


    private val bottomNavFragments = mapOf(
        R.id.yourTicketsFragment to R.id.your_tickets,
        R.id.favoriteFlightFragment to R.id.favorite_flights,
        R.id.buyTicketAiroportFragment to R.id.buy_tickets,
        R.id.privateInfoFragment to R.id.profil_info,
        R.id.airportListFragment to R.id.airoport_list
    )

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
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment
                val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

                when (currentFragment) {
                    is AirportListFragment -> {
                        finish()
                    }
                    else -> {
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

        binding.navView.setOnItemSelectedListener { item ->
            if (!isNavigating) {
                isNavigating = true
                when (item.itemId) {
                    R.id.your_tickets -> {
                        navController.navigate(R.id.yourTicketsFragment)
                    }
                    R.id.favorite_flights -> {
                        navController.navigate(R.id.favoriteFlightFragment)
                    }
                    R.id.buy_tickets -> {
                        navController.navigate(R.id.buyTicketAiroportFragment)
                    }
                    R.id.profil_info -> {
                        navController.navigate(R.id.privateInfoFragment)
                    }
                    R.id.airoport_list -> {
                        navController.navigate(R.id.airportListFragment)
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

            // Обновляем выделение в меню
            updateMenuSelection(destination.id)
        }
    }

    private fun updateMenuSelection(destinationId: Int) {

        val menuItemId = bottomNavFragments[destinationId]

        if (menuItemId != null) {

            if (binding.navView.selectedItemId != menuItemId) {
                isNavigating = true
                binding.navView.selectedItemId = menuItemId
                isNavigating = false
            }
        } else {
            // Если это не один из фрагментов меню, снимаем выделение со всех элементов
            clearMenuSelection()
        }
    }

    private fun clearMenuSelection() {
        (binding.navView as? CustomBottomNavigationView)?.clearSelection()
    }
}