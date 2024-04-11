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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.constance.Constant
import com.example.habitstracker.databinding.FragmentHabitListBinding
import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.common.models.HabitType
import com.example.habitstracker.ui.screens.home.helpers.HabitAdapter
import com.example.habitstracker.ui.screens.home.helpers.HabitDiffUtilCallback
import kotlinx.coroutines.launch

class HabitListFragment : Fragment() {
    private lateinit var binding: FragmentHabitListBinding
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.navigation_graph)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHabitListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        val pos = arguments?.getInt(POSITION_ARG)
        val adapter = HabitAdapter { goToEditFragment(it, navController) }
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            viewModel.getViewState().collect { viewState ->
                if (!viewState.habitsByType.containsKey(HabitType.getFromPosition(pos))) return@collect

                val oldHabits = adapter.habits.toList()
                val newHabits = viewState.habitsByType[HabitType.getFromPosition(pos)]!!
                adapter.habits = newHabits
                val diffCallback = HabitDiffUtilCallback(oldHabits, newHabits)
                val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
                diffResult.dispatchUpdatesTo(adapter)
            }
        }
    }

    private fun goToEditFragment(habit: Habit, navController: NavController) {
        val bundle = bundleOf(Constant.HABIT_KEY to habit)
        navController.navigate(R.id.editFragment, bundle)
    }

    companion object {
        const val POSITION_ARG = "query_key"
        fun newInstance(key: Int): HabitListFragment {
            val fragment = HabitListFragment()
            Bundle().also {
                it.putInt(POSITION_ARG, key)
                fragment.arguments = it
            }
            return fragment
        }
    }
}