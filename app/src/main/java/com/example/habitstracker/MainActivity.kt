package com.example.habitstracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.habitstracker.databinding.ActivityMainBinding
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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                if (binding.drawer.isOpen) binding.drawer.close() else binding.drawer.open()
            }
        }
        return true
    }

    private fun initNavView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.place_holder) as NavHostFragment
        val navController = navHostFragment.navController

        binding.drawerNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.action_aboutAsFragment_to_homeFragment)
                    }
                }

                R.id.inf -> {
                    if (navController.currentDestination?.id != R.id.aboutAsFragment) {
                        navController.navigate(R.id.aboutAsFragment)
                    }
                }
            }
            binding.drawer.close()
            true
        }
    }
}