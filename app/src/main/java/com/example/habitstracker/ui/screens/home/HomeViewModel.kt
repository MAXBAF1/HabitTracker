package com.example.habitstracker.ui.screens.home

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import kotlinx.coroutines.flow.update

class HomeViewModel :
    BaseViewModel<HomeViewState, HomeEvent>(initialState = HomeViewState.HabitsChanged()) {
    private val habitsByType: Map<HabitType, MutableList<Habit>> = mapOf(
        HabitType.Good to mutableListOf(),
        HabitType.Bad to mutableListOf(),
    )

    override fun obtainEvent(viewEvent: HomeEvent) {
        when (viewEvent) {
            is HomeEvent.RestoreHabits -> restoreHabits(viewEvent.newHabit)
        }
    }

    private fun restoreHabits(newHabit: Habit?) {
        if (newHabit == null) return
        val list = habitsByType[newHabit.type] ?: throw IllegalArgumentException("A new tab has not been processed")
        if (!list.contains(newHabit)) list.add(newHabit)
        viewState.update { HomeViewState.HabitsChanged(habitsByType, newHabit.type) }
    }
}