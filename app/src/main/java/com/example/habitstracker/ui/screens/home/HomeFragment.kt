package com.example.habitstracker.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.constance.Constant
import com.example.habitstracker.databinding.FragmentHomeBinding
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.screens.home.helpers.HabitAdapter
import com.example.habitstracker.ui.screens.home.helpers.HabitDiffUtilCallback
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import com.example.habitstracker.utils.getSerializable
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private lateinit var binding: FragmentHomeBinding
    private val adapter = HabitAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        val habit = getSerializable<Habit>(Constant.HABIT_KEY)

        HabitAdapter.onItemClick = {
            val bundle = bundleOf(Constant.HABIT_KEY to it)
            navController.navigate(R.id.editFragment, bundle)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener { navController.navigate(R.id.editFragment) }

        viewModel.obtainEvent(HomeEvent.RestoreHabits(habit))
        lifecycleScope.launch {
            viewModel.getViewState().collect { viewState ->
                when (viewState) {
                    is HomeViewState.HabitsRestored -> updateHabits(viewState.habits)
                }
            }
        }
    }

    private fun updateHabits(newHabits: List<Habit>) {
        val diffCallback = HabitDiffUtilCallback(adapter.habits, newHabits)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(adapter)
        adapter.habits = newHabits
    }
}