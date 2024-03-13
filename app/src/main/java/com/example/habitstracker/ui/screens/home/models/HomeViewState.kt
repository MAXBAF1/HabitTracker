package com.example.habitstracker.ui.screens.home.models

import com.example.habitstracker.ui.global_models.Habit

sealed class HomeViewState {
    data class HabitsRestored(val habits: List<Habit> = listOf()) : HomeViewState()
}