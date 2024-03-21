package com.example.habitstracker.ui.screens.home.models

import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType

sealed class HomeEvent {
    data class RestoreHabits(val newHabit: Habit?) : HomeEvent()
}