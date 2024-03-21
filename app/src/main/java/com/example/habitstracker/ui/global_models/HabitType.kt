package com.example.habitstracker.ui.global_models

import com.example.habitstracker.R

enum class HabitType(val strId: Int) {
    Good(R.string.type_good), Bad(R.string.type_bad);

    companion object {
        fun getFromPosition(position: Int?): HabitType {
            return when (position) {
                0 -> Good
                1 -> Bad
                else -> throw IllegalArgumentException("A new tab has not been processed")
            }
        }
    }
}