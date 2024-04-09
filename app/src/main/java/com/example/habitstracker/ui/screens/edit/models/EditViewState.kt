package com.example.habitstracker.ui.screens.edit.models

import com.example.habitstracker.ui.common.models.Habit

sealed class EditViewState {
    data class HabitRestored(val habit: Habit? = null) : EditViewState()
    data class HabitSaved(val habit: Habit?) : EditViewState()
}
