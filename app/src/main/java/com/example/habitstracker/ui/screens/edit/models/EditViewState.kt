package com.example.habitstracker.ui.screens.edit.models

import com.example.habitstracker.ui.global_models.Habit

sealed class EditViewState {
    data class HabitsRestored(val habit: Habit? = null) : EditViewState()
}
