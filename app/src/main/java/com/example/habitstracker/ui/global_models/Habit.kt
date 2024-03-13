package com.example.habitstracker.ui.global_models

import java.util.UUID

data class Habit(
    var habitName: String,
    var desc: String,
    var priorityPos: Int,
    var type: HabitType,
    var period: String,
    var color: String,
    val id: UUID = UUID.randomUUID(),
) : java.io.Serializable
