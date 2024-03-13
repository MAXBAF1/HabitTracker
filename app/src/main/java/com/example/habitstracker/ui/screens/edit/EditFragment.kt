package com.example.habitstracker.ui.screens.edit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.R
import com.example.habitstracker.constance.Constance
import com.example.habitstracker.databinding.FragmentEditBinding
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType
import com.example.habitstracker.ui.screens.edit.models.EditEvent
import com.example.habitstracker.ui.screens.edit.models.EditViewState
import com.example.habitstracker.ui.screens.edit.models.FieldType
import com.example.habitstracker.utils.getSerializable
import kotlinx.coroutines.launch

class EditFragment : Fragment() {
    private val viewModel: EditViewModel by hiltNavGraphViewModels(R.id.navigation_graph)
    private var habit: Habit? = null
    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        habit = getSerializable(Constance.HABIT_KEY)

        initPriorities(onPrioritySelected = { viewModel.obtainEvent(EditEvent.ChangePriority(it)) })
        onClickColor(onClick = { viewModel.obtainEvent(EditEvent.ChangeColor(it)) })

        binding.btnCancel.setOnClickListener { navController.popBackStack() }
        binding.btnSave.setOnClickListener {
            viewModel.obtainEvent(EditEvent.onBtnSaveClick)
            val bundle = bundleOf(Constance.HABIT_KEY to getHabit())
            navController.navigate(R.id.homeFragment, bundle)
        }

        binding.edHabitName.addTextChangedListener {
            viewModel.obtainEvent(EditEvent.ChangeFieldText(FieldType.Name, it.toString()))
        }
        binding.edDesc.addTextChangedListener {
            viewModel.obtainEvent(EditEvent.ChangeFieldText(FieldType.Desc, it.toString()))
        }
        binding.edHabitCnt.addTextChangedListener {
            viewModel.obtainEvent(EditEvent.ChangeFieldText(FieldType.RepeatCnt, it.toString()))
        }
        binding.edHabitPeriod.addTextChangedListener {
            viewModel.obtainEvent(EditEvent.ChangeFieldText(FieldType.Period, it.toString()))
        }
        binding.rgType.setOnCheckedChangeListener { rg, i ->
            val type = if (i == R.id.rbGood) {
                HabitType.Good
            } else HabitType.Bad
            viewModel.obtainEvent(EditEvent.ChangeHabitType(type))
        }

        viewModel.obtainEvent(EditEvent.RestoreEdits(habit))
        lifecycleScope.launch {
            viewModel.getViewState().collect { viewState ->
                when (viewState) {
                    is EditViewState.HabitsRestored -> habit?.let { initEdition(it) }
                }
            }
        }
    }

    private fun initEdition(habit: Habit) = with(binding) {
        edHabitName.setText(habit.habitName)
        edDesc.setText(habit.desc)
        spPriority.setSelection(habit.priorityPos)
        edHabitPeriod.setText(habit.period)
        color.setBackgroundColor(Color.parseColor(habit.color))
    }

    private fun initPriorities(onPrioritySelected: (Int) -> Unit) {
        val priorities = resources.getStringArray(R.array.Priorities)

        val arrayAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, priorities
        )
        binding.spPriority.adapter = arrayAdapter

        binding.spPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                onPrioritySelected(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun onClickColor(onClick: (String) -> Unit) = with(binding) {
        val views = arrayOf(
            aqua,
            black,
            blue,
            fuchsia,
            gray,
            green,
            lime,
            maroon,
            navy,
            olive,
            purple,
            red,
            silver,
            teal,
            white,
            yellow
        )
        views.forEach { view ->
            view.setOnClickListener {
                color.setBackgroundColor(Color.parseColor(it.transitionName))
                onClick(it.transitionName)
            }
        }
    }

    companion object {
        fun newInstance() = EditFragment()
    }
}