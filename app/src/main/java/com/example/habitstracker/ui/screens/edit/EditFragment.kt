package com.example.habitstracker.ui.screens.edit

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.HorizontalScrollView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
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
        arguments?.clear()

        initPriorities(onPrioritySelected = { viewModel.obtainEvent(EditEvent.ChangePriority(it)) })
        onClickColor(onClick = { viewModel.obtainEvent(EditEvent.ChangeColor(it)) })

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
        binding.rgType.setOnCheckedChangeListener { _, i ->
            val type = if (i == R.id.rbGood) HabitType.Good else HabitType.Bad
            viewModel.obtainEvent(EditEvent.ChangeHabitType(type))
        }
        binding.btnCancel.setOnClickListener { navController.popBackStack() }
        binding.btnSave.setOnClickListener { viewModel.obtainEvent(EditEvent.ClickBtnSave) }

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            if (navDestination.id == R.id.homeFragment && habit != null) {
                viewModel.obtainEvent(EditEvent.ExitToHome)
                habit = null
            }
        }

        viewModel.obtainEvent(
            EditEvent.RestoreEdits(
                habit ?: Habit("", "", 0, HabitType.Good, "", "", Color.BLACK), habit != null
            )
        )
        lifecycleScope.launch {
            viewModel.getViewState().collect { viewState ->
                when (viewState) {
                    is EditViewState.HabitRestored -> viewState.habit?.let { initEdits(it) }
                    is EditViewState.HabitSaved -> {
                        val bundle = bundleOf(Constance.HABIT_KEY to viewState.habit)
                        navController.navigate(R.id.action_editFragment_to_homeFragment, bundle)
                    }
                }
            }
        }
    }

    private fun initEdits(habit: Habit) = with(binding) {
        edHabitName.setText(habit.habitName)
        edDesc.setText(habit.desc)
        spPriority.setSelection(habit.priorityPos)
        edHabitPeriod.setText(habit.period)
        color.setBackgroundColor(habit.color)
        when (habit.type) {
            HabitType.Good -> rgType.check(R.id.rbGood)
            HabitType.Bad -> rgType.check(R.id.rbBad)
        }
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

    private fun onClickColor(onClick: (Int) -> Unit) = with(binding) {
        val childCount = binding.gradLinLay.childCount

        for (i in 0 until childCount) {
            val childView = binding.gradLinLay.getChildAt(i)

            childView.setOnClickListener {
                val squareColor = getCenterColorOfSquare(it)
                color.setBackgroundColor(squareColor)
                onClick(squareColor)
            }
        }
    }

    private fun getCenterColorOfSquare(squareView: View): Int {
        val squareLocation = getSquareCoordinates(squareView, binding.colorsScroll)

        val squareCenterX = squareLocation.first + squareView.width / 2
        val squareCenterY = squareLocation.second + squareView.height / 2

        val bgDrawable = binding.gradLinLay.background
        val bitmap = bgDrawable.toBitmap(binding.gradLinLay.width, binding.gradLinLay.height)

        return bitmap.getPixel(squareCenterX, squareCenterY)
    }

    private fun getSquareCoordinates(squareView: View, scrollView: HorizontalScrollView): Pair<Int, Int> {
        val linearLayoutLocation = IntArray(2)
        scrollView.getLocationInWindow(linearLayoutLocation)

        val squareLocation = IntArray(2)
        squareView.getLocationInWindow(squareLocation)

        val relativeX = squareLocation[0] - linearLayoutLocation[0] + scrollView.scrollX
        val relativeY = squareLocation[1] - linearLayoutLocation[1]

        return Pair(relativeX, relativeY)
    }
}