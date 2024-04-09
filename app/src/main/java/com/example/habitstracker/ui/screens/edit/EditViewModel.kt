package com.example.habitstracker.ui.screens.edit

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.screens.edit.models.EditEvent
import com.example.habitstracker.ui.screens.edit.models.EditViewState
import com.example.habitstracker.ui.screens.edit.models.FieldType
import kotlinx.coroutines.flow.update

class EditViewModel : BaseViewModel<EditViewState, EditEvent>(
    initialState = EditViewState.HabitRestored()
) {
    private var habit: Habit? = null

    override fun obtainEvent(viewEvent: EditEvent) {
        when (viewEvent) {
            is EditEvent.RestoreEdits -> {
                if (viewEvent.isEdit || viewEvent.habit != null && habit == null) {
                    habit = viewEvent.habit
                }
                viewState.update { EditViewState.HabitRestored(habit) }
            }

            is EditEvent.ChangeColor -> habit?.color = viewEvent.color
            is EditEvent.ChangePriority -> habit?.priorityPos = viewEvent.priorityPos
            is EditEvent.ChangeFieldText -> {
                when (viewEvent.type) {
                    FieldType.Name -> habit?.habitName = viewEvent.text
                    FieldType.Desc -> habit?.desc = viewEvent.text
                    FieldType.RepeatCnt -> habit?.repeatCnt = viewEvent.text
                    FieldType.Period -> habit?.period = viewEvent.text
                }
            }

            is EditEvent.ChangeHabitType -> habit?.type = viewEvent.type
            is EditEvent.ExitToHome -> habit = null

            EditEvent.ClickBtnSave -> {
                viewState.update { EditViewState.HabitSaved(habit) }
                habit = null
            }
        }
    }
}