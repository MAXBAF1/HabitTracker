package com.example.habitstracker.ui.screens.home.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.databinding.HabitItemBinding
import com.example.habitstracker.ui.common.models.Habit

class HabitAdapter(private val onHabitClick: ((Habit) -> Unit)) :
    RecyclerView.Adapter<HabitAdapter.HabitHolder>() {
    var habits: List<Habit> = listOf()

    class HabitHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        private val priorities = item.resources.getStringArray(R.array.Priorities)
        private val binding = HabitItemBinding.bind(item)

        fun bind(habit: Habit) = with(binding) {
            tvHabitName.text = habit.habitName
            tvDesc.text = habit.desc
            tvPriority.text = priorities[habit.priorityPos]
            tvType.text = item.context.getString(habit.type.strId)
            tvPeriod.text = habit.period
            color.setBackgroundColor(habit.color)

            if (habit.desc.isEmpty()) tvDesc.visibility = View.GONE
            if (habit.period.isEmpty()) {
                tvPeriodLabel.visibility = View.GONE
                tvPeriod.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitHolder(view)
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        val habit = habits[position]

        holder.bind(habit)
        holder.itemView.setOnClickListener { onHabitClick.invoke(habit) }
    }

    override fun getItemCount(): Int {
        return habits.size
    }
}