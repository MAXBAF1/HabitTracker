package com.example.habitstracker.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State, Event>(initialState: State) : ViewModel() {
    protected val viewState = MutableStateFlow(initialState)

    fun getViewState(): StateFlow<State> = viewState

    abstract fun obtainEvent(viewEvent: Event)
}