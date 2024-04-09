package com.example.habitstracker.ui.screens.home.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.ui.common.models.HabitType

class ViewPagerAdapter(
    var habitsByType: MutableMap<HabitType, HabitAdapter> = mutableMapOf(),
) : RecyclerView.Adapter<ViewPagerAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_habits_list, parent, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rcView)
        recyclerView.layoutManager =
            LinearLayoutManager(parent.context, RecyclerView.VERTICAL, false)

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (habitsByType.isEmpty()) return
        val type = HabitType.getFromPosition(position)
        val adapter = habitsByType[type]
        holder.recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int = habitsByType.size

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.rcView)
    }
}
