package com.example.habitstracker.ui.screens.home.helpers

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.databinding.HabitItemBinding
import com.example.habitstracker.ui.global_models.Habit

class HabitAdapter : RecyclerView.Adapter<HabitAdapter.HabitHolder>() {
    var habits: List<Habit> = listOf()

    class HabitHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val priorities = item.resources.getStringArray(R.array.Priorities)
        private val binding = HabitItemBinding.bind(item)

        fun bind(habit: Habit) = with(binding) {
            tvHabitName.text = habit.habitName
            tvDesc.text = habit.desc
            tvPriority.text = priorities[habit.priorityPos]
            tvType.text = habit.type
            tvPeriod.text = habit.period
            color.setBackgroundColor(Color.parseColor(habit.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)

        return HabitHolder(view)
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        holder.bind(habits[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(habits[position])
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    companion object {
        var onItemClick: ((Habit) -> Unit)? = null
    }
}