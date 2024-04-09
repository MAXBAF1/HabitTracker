package com.example.habitstracker.ui.screens.home.helpers

import androidx.recyclerview.widget.DiffUtil
import com.example.habitstracker.ui.common.models.Habit

class HabitDiffUtilCallback(
    private val oldList: List<Habit>,
    private val newList: List<Habit>,
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}