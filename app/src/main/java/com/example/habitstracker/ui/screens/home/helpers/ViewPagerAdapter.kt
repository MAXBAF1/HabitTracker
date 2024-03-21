package com.example.habitstracker.ui.screens.home.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.ui.global_models.Habit
import com.example.habitstracker.ui.global_models.HabitType

class ViewPagerAdapter(
    var habitsByType: Map<HabitType, List<Habit>> = mapOf(
        HabitType.Good to mutableListOf(),
        HabitType.Bad to mutableListOf(),
    ),
    onHabitClick: (Habit) -> Unit,
) : RecyclerView.Adapter<ViewPagerAdapter.RecyclerViewHolder>() {
    private val adapter = HabitAdapter(onHabitClick)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_habits_list, parent, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rcView)
        recyclerView.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

    }

    fun updateHabits(type: HabitType) {
        if (habitsByType.isEmpty()) return

        val newHabits =
            habitsByType[type] ?: throw IllegalArgumentException("A new tab has not been processed")
        val diffCallback = HabitDiffUtilCallback(adapter.habits, newHabits)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(adapter)
        adapter.habits = newHabits
    }


    override fun getItemCount(): Int = habitsByType.size

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.rcView)
    }
}
