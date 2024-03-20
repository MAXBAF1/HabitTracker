package com.example.habitstracker.ui.screens.home.models

import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType

sealed class HomeViewState {
    data class HabitsChanged(
        val habits: List<Habit> = listOf(),
        val toType: HabitType = HabitType.Good,
    ) : HomeViewState()
}