package com.example.habitstracker.ui.screens.home

import com.example.habitstracker.ui.BaseViewModel
import com.example.habitstracker.ui.common.models.Habit
import com.example.habitstracker.ui.common.models.HabitType
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import kotlinx.coroutines.flow.update

class HomeViewModel : BaseViewModel<HomeViewState, HomeEvent>(initialState = HomeViewState()) {
    private val habitsByType: Map<HabitType, MutableList<Habit>> = mapOf(
        HabitType.Good to mutableListOf(),
        HabitType.Bad to mutableListOf(),
    )

    private var searchText = ""


    override fun obtainEvent(viewEvent: HomeEvent) {
        when (viewEvent) {
            is HomeEvent.RestoreHabits -> restoreHabits(viewEvent.newHabit)
            is HomeEvent.ChangeSearchFilterText -> updateWithVisibleHabits(viewEvent.text)
        }
    }

    private fun updateWithVisibleHabits(name: String? = null) {
        var visibleHabitsByType: Map<HabitType, MutableList<Habit>> = mapOf(
            HabitType.Good to mutableListOf(),
            HabitType.Bad to mutableListOf(),
        )
        if (searchText.isNotBlank() || !name.isNullOrBlank()) {
            if (name != null) searchText = name
            habitsByType.keys.forEach { type ->
                habitsByType[type]?.forEach {
                    if (it.habitName.contains(searchText)) visibleHabitsByType[type]?.add(it)
                }
            }
        } else visibleHabitsByType = habitsByType

        viewState.update {
            it.copy(habitsByType = visibleHabitsByType, searchText = searchText)
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

        updateWithVisibleHabits()
    }
}