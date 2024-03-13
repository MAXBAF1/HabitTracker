package com.example.habitstracker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.ui.global_models.Habit

open class DataModel: ViewModel() {
    val messageForActivity: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val habit: MutableLiveData<Habit> by lazy {
        MutableLiveData<Habit>()
    }

}