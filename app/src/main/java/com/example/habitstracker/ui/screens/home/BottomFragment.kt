package com.example.habitstracker.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.example.habitstracker.R
import com.example.habitstracker.databinding.BottomSheetLayoutBinding
import com.example.habitstracker.ui.screens.home.models.HomeEvent
import com.example.habitstracker.ui.screens.home.models.HomeViewState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch


private const val COLLAPSED_HEIGHT = 228

class BottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLayoutBinding
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.navigation_graph)

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            BottomSheetLayoutBinding.bind(inflater.inflate(R.layout.bottom_sheet_layout, container))
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()

        binding.searcher.addTextChangedListener {
            viewModel.obtainEvent(HomeEvent.ChangeSearchFilterText(it.toString()))
        }

        lifecycleScope.launch {
            viewModel.getViewState().collect { viewState ->
                if (binding.searcher.text.isNullOrEmpty())
                    binding.searcher.setText(viewState.searchText)
            }
        }
    }

    private fun initDialog() {
        val density = requireContext().resources.displayMetrics.density

        dialog?.let {
            val bottomSheet =
                it.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}
                override fun onSlide(p0: View, p1: Float) {}
            })
        }
    }
}

