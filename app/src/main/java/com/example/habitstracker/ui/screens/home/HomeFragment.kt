package com.example.habitstracker.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.habitstracker.R
import com.example.habitstracker.constance.Constant
import com.example.habitstracker.databinding.FragmentHomeBinding
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType
import com.example.habitstracker.ui.screens.home.helpers.HabitAdapter
import com.example.habitstracker.ui.screens.home.helpers.ViewPagerAdapter
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import com.example.habitstracker.utils.getSerializable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

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

        val adapters = mutableMapOf(
            HabitType.Good to HabitAdapter { goToEditFragment(it, navController) },
            HabitType.Bad to HabitAdapter { goToEditFragment(it, navController) },
        )
        val adapter = ViewPagerAdapter(adapters)
        setupViewPager(adapter)

        binding.fabAdd.setOnClickListener { navController.navigate(R.id.editFragment) }

        viewModel.obtainEvent(HomeEvent.RestoreHabits(newHabit = habit))
        lifecycleScope.launch {
            viewModel.getViewState().collect { viewState ->
                when (viewState) {
                    is HomeViewState.HabitsChanged -> {
                        if (viewState.habitsByType.isEmpty()) return@collect
                        adapter.habitsByType.keys.forEach {
                            adapter.habitsByType[it]?.habits = viewState.habitsByType[it]!!
                        }
                    }
                }
            }
        }
    }

    private fun setupViewPager(adapter: ViewPagerAdapter) {
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

    private fun goToEditFragment(habit: Habit, navController: NavController) {
        val bundle = bundleOf(Constant.HABIT_KEY to habit)
        navController.navigate(R.id.editFragment, bundle)
    }
}