package com.example.habitstracker.ui.screens.home

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.common.models.HabitType
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
        if (newHabit == null || !habitsByType.containsKey(newHabit.type)) return

        val goodList = habitsByType[HabitType.Good]!!
        val badList = habitsByType[HabitType.Bad]!!

        val goodListIndex = goodList.indexOf(newHabit)
        val badListIndex = badList.indexOf(newHabit)

        if (newHabit.type != HabitType.Good && goodListIndex != -1) {
            goodList.removeAt(goodListIndex)
            badList.add(newHabit)
        } else if (newHabit.type != HabitType.Bad && badListIndex != -1) {
            badList.removeAt(badListIndex)
            goodList.add(newHabit)
        } else if (goodListIndex == -1 && badListIndex == -1) {
            habitsByType[newHabit.type]?.add(newHabit)
        }

        viewState.update { HomeViewState.HabitsChanged(habitsByType, newHabit.type) }
    }
}