package com.example.habitstracker.ui.screens.edit

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType
import com.example.habitstracker.ui.screens.edit.models.EditEvent
import com.example.habitstracker.ui.screens.edit.models.EditViewState
import com.example.habitstracker.ui.screens.edit.models.FieldType
import kotlinx.coroutines.flow.update

class EditViewModel : BaseViewModel<EditViewState, EditEvent>(
    initialState = EditViewState.HabitsRestored()
) {
    private var habit = Habit("", "", 0, HabitType.Good, "", "#00FFFF")

    override fun obtainEvent(viewEvent: EditEvent) {
        when (viewEvent) {
            is EditEvent.RestoreEdits -> {
                viewEvent.habit?.let { habit = it }

                viewState.update { EditViewState.HabitsRestored(habit) }
            }

            is EditEvent.ChangeColor -> habit.color = viewEvent.color
            is EditEvent.ChangePriority -> habit.priorityPos = viewEvent.priorityPos
            is EditEvent.ChangeFieldText -> {
                when (viewEvent.type) {
                    FieldType.Name -> habit.habitName = viewEvent.text
                    FieldType.Desc -> habit.desc = viewEvent.text
                    FieldType.RepeatCnt -> habit. = viewEvent.text
                    FieldType.Period -> habit.period = viewEvent.text
                }
            }
            is EditEvent.ChangeHabitType -> TODO()
            EditEvent.onBtnSaveClick -> TODO()
        }
    }
}