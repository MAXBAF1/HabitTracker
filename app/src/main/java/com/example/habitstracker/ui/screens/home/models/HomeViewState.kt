package com.example.habitstracker.ui.screens.home.models

import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType

sealed class HomeViewState {
    data class HabitsChanged(
        val habitsByType: Map<HabitType, List<Habit>> = mapOf(),
        val toType: HabitType = HabitType.Good
    ) : HomeViewState()
}