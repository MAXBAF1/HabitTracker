package com.example.habitstracker.ui.screens.aboutAs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.habitstracker.databinding.FragmentAboutAsBinding

class AboutAsFragment : Fragment() {
    lateinit var binding: FragmentAboutAsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutAsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance() = AboutAsFragment()
    }
}