package com.example.habitstracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.habitstracker.databinding.ActivityMainBinding
import com.example.habitstracker.ui.screens.edit.EditFragment
import com.example.habitstracker.ui.screens.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavView()
    }

    private fun initNavView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.place_holder) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    binding.drawer.closeDrawers()
                    navController.navigate(R.id.homeFragment)
                }

                R.id.inf -> {
                    binding.drawer.closeDrawers()
                    navController.navigate(R.id.aboutAsFragment)
                }
            }
            true
        }
    }
}