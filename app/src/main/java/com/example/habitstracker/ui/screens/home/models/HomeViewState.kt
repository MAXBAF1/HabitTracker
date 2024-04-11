package com.example.habitstracker.ui.screens.home.models

import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.common.models.HabitType


data class HomeViewState(
    val habitsByType: Map<HabitType, List<Habit>> = mapOf(),
    val searchText: String = ""
)
