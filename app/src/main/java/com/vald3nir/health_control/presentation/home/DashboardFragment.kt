package com.vald3nir.health_control.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vald3nir.core_ui.CoreFragment
import com.vald3nir.core_ui.components.CustomDifferAdapter
import com.vald3nir.core_ui.extensions.setupDefaultLayoutManager
import com.vald3nir.health_control.data.dto.ExamDTO
import com.vald3nir.health_control.databinding.FragmentDashboardBinding
import com.vald3nir.health_control.databinding.ItemViewDashboardBinding
import com.vald3nir.health_control.domain.common.diff.examDiffUtil
import com.vald3nir.health_control.domain.common.extensions.setTextColorInt
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : CoreFragment() {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: HomeViewModel by sharedViewModel()

    private val dashboardAdapter by lazy {
        val adapter = CustomDifferAdapter(bindingInflater = ItemViewDashboardBinding::inflate,
            list = emptyList(),
            itemDiffUtil = examDiffUtil(),
            onBind = { item, binding, _, _ ->
                binding.bindItem(item)
            })
        adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
        viewModel.loadExams()
    }

    private fun setupObservers() {
        viewModel.exams.observe(viewLifecycleOwner) {
            dashboardAdapter.submitList(it.toMutableList())
        }
    }

    private fun initViews() = with(binding) {
        rvExams.setupDefaultLayoutManager()
        rvExams.adapter = dashboardAdapter
        btnInclude.setOnClickListener { redirectToExamDetailFragment() }
    }

    private fun ItemViewDashboardBinding.bindItem(exam: ExamDTO) {
        root.setOnClickListener { redirectToExamDetailFragment(exam, ExamDetailState.SeeExam) }
        txvDate.text = exam.date
        txvStatus.apply {
            text = exam.status.text
            setTextColorInt(exam.status.color)
        }
    }

    private fun redirectToExamDetailFragment(
        exam: ExamDTO? = null,
        state: ExamDetailState = ExamDetailState.NewExam
    ) {
        viewModel.currentExamDTO = exam
        viewModel.state = state
        navigateTo(DashboardFragmentDirections.redirectToExamDetailFragment())
    }
}