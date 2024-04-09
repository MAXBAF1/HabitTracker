package com.example.habitstracker.ui.screens.edit.models

import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.common.models.HabitType

sealed class EditEvent {
    data class RestoreEdits(val habit: Habit?, val isEdit: Boolean) : EditEvent()
    data class ChangeColor(val color: Int) : EditEvent()
    data class ChangePriority(val priorityPos: Int) : EditEvent()
    data class ChangeFieldText(val type: FieldType, val text: String) : EditEvent()
    data class ChangeHabitType(val type: HabitType) : EditEvent()
    object ClickBtnSave : EditEvent()
    object ExitToHome : EditEvent()
}
