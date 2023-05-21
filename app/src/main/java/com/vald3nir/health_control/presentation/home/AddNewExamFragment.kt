package com.vald3nir.health_control.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.core_ui.CoreFragment
import com.vald3nir.health_control.R
import com.vald3nir.health_control.databinding.FragmentAddNewExamBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.vald3nir.commons.R as RCommons


class AddNewExamFragment : CoreFragment() {

    private lateinit var binding: FragmentAddNewExamBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewExamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        if (viewModel.currentExamDTO != null) {
            setupNavigator(R.string.exam_detail)
        } else {
            setupNavigator(R.string.new_exam)
        }
    }

    private fun setupNavigator(titleID: Int) = with(binding.navigator) {
        setTitle(getString(titleID))
        enableClickEvents(requireCoreActivity())
        updateIconsCor(RCommons.color.secondary_color)
    }
}