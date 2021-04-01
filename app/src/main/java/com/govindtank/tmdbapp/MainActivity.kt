package com.govindtank.tmdbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
//import com.Theme.tmdbapp.databinding.ActivityMainBinding
//import com.Theme.tmdbapp.presentation.MainViewModel
//import com.Theme.tmdbapp.util.hideKeyboard
//import com.Theme.tmdbapp.util.snackNBite
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.govindtank.tmdbapp.databinding.ActivityMainBinding
import com.govindtank.tmdbapp.presentation.MainViewModel
import com.govindtank.tmdbapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible =
                destination.id != R.id.detailFragment && destination.id != R.id.searchFragment
            hideKeyboard()
            if (destination.id in arrayOf(
                    R.id.movies_page_fragment,
                    R.id.watch_later_page_fragment
                )
            ) {
                mainViewModel.submitMovieQuery(null)
            }
        }
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }

}