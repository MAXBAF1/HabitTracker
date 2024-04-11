package com.example.habitstracker.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habitstracker.R
import com.example.habitstracker.constance.Constant
import com.example.habitstracker.databinding.FragmentHomeBinding
import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.screens.home.helpers.HabitListAdapter
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.utils.getSerializable
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val habit = getSerializable<Habit>(Constant.HABIT_KEY)
        val navController = findNavController()
        initBottomSheet()
        val adapter = HabitListAdapter(this)
        setupViewPager(adapter)

        binding.fabAdd.setOnClickListener { navController.navigate(R.id.editFragment) }

        viewModel.obtainEvent(HomeEvent.RestoreHabits(newHabit = habit))
    }

    private fun initBottomSheet() {
        binding.fabFilter.setOnClickListener {
            BottomFragment().show(parentFragmentManager, "tag")
        }
    }

    private fun setupViewPager(adapter: FragmentStateAdapter) {
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            val textId = when (pos) {
                0 -> R.string.good
                1 -> R.string.bad
                else -> throw IllegalArgumentException("A new tab has not been processed")
            }
            tab.text = requireContext().getString(textId)
        }.attach()
    }
}