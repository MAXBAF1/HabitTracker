package com.example.habitstracker.ui.screens.edit.models

import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType

sealed class EditEvent {
    data class RestoreEdits(val habit: Habit?) : EditEvent()
    data class ChangeColor(val color: String) : EditEvent()
    data class ChangePriority(val priorityPos: Int) : EditEvent()
    data class ChangeFieldText(val type: FieldType, val text: String) : EditEvent()
    data class ChangeHabitType(val type: HabitType) : EditEvent()
    object onBtnSaveClick : EditEvent()
}
