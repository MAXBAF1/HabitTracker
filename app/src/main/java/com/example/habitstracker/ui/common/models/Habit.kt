package com.example.habitstracker.ui.common.models

import android.graphics.Color
import java.util.UUID

data class Habit(
    var habitName: String = "",
    var desc: String = "",
    var priorityPos: Int = 0,
    var type: HabitType = HabitType.Good,
    var period: String = "",
    var repeatCnt: String = "",
    var color: Int = Color.BLACK,
    val id: UUID = UUID.randomUUID(),
) : java.io.Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Habit

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}