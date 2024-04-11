package com.example.habitstracker.ui.screens.home.helpers

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.habitstracker.ui.screens.home.HabitListFragment

class HabitListAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return HabitListFragment.newInstance(position)
    }
}
