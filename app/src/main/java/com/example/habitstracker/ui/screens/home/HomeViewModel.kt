package com.example.habitstracker.ui.screens.home

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import kotlinx.coroutines.flow.update

class HomeViewModel : BaseViewModel<HomeViewState, HomeEvent>(initialState = HomeViewState.HabitsRestored()) {
    private val habits: ArrayList<Habit> = arrayListOf()
    override fun obtainEvent(viewEvent: HomeEvent) {
        when (viewEvent) {
            is HomeEvent.RestoreHabits -> {
                if (viewEvent.newHabit != null && !habits.contains(viewEvent.newHabit)) {
                    habits.add(viewEvent.newHabit)
                }
                viewState.update {
                    HomeViewState.HabitsRestored(habits)
                }
            }
        }
    }
}