package com.example.habitstracker.ui.screens.home

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import kotlinx.coroutines.flow.update

class HomeViewModel :
    BaseViewModel<HomeViewState, HomeEvent>(initialState = HomeViewState.HabitsChanged()) {
    private var tabType: HabitType = HabitType.Good
    private val goodHabits: MutableList<Habit> = arrayListOf()
    private val badHabits: MutableList<Habit> = arrayListOf()

    override fun obtainEvent(viewEvent: HomeEvent) {
        when (viewEvent) {
            is HomeEvent.RestoreHabits -> restoreHabits(viewEvent.newHabit)
            is HomeEvent.ChangeActiveType -> changeActiveType(viewEvent.type)
        }
    }

    private fun changeActiveType(type: HabitType) {
        tabType = type
        when (type) {
            HabitType.Good -> viewState.update { HomeViewState.HabitsChanged(goodHabits, tabType) }
            HabitType.Bad -> viewState.update { HomeViewState.HabitsChanged(badHabits, tabType) }
        }
    }

    private fun restoreHabits(newHabit: Habit?) {
        if (newHabit == null) return

       when (newHabit.type) {
            HabitType.Good -> {
                if (!goodHabits.contains(newHabit)) goodHabits.add(newHabit)
                viewState.update { HomeViewState.HabitsChanged(goodHabits, HabitType.Good) }
            }

            HabitType.Bad -> {
                if (!badHabits.contains(newHabit)) badHabits.add(newHabit)
                viewState.update { HomeViewState.HabitsChanged(badHabits, HabitType.Bad) }

            }
        }
    }
}