package com.vald3nir.health_control.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import com.vald3nir.commons.R as RCommons
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.vald3nir.core_ui.CoreFragment
import com.vald3nir.health_control.R
import com.vald3nir.health_control.databinding.FragmentExamDetailBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

enum class ExamDetailState { SeeExam, EditExam, NewExam }


class ExamDetailFragment : CoreFragment() {

    private lateinit var binding: FragmentExamDetailBinding
    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun enableEditExamDetailState() {
        viewModel.enableEditExamDetailState()
        initViews()
    }

    private fun initViews() {
        setupHeader()
        setupNavigator()
        setupConfirmButton()
        setupInputComponent()
    }

    private fun setupHeader() = with(binding) {
        when (viewModel.state) {
            ExamDetailState.SeeExam -> {
                txvSubtitle.isVisible = false
                txvEditExam.isVisible = true
                txvEditExam.setOnClickListener { enableEditExamDetailState() }
            }

            ExamDetailState.EditExam -> {
                txvSubtitle.isVisible = true
                txvEditExam.isVisible = false
            }

            ExamDetailState.NewExam -> {
                txvSubtitle.isVisible = true
                txvEditExam.isVisible = false
            }
        }
    }

    private fun setupNavigator() = with(binding.navigator) {
        enableClickEvents(requireCoreActivity())
        updateIconsCor(RCommons.color.secondary_color)
        when (viewModel.state) {
            ExamDetailState.SeeExam -> {
                setTitle(R.string.exam_detail)
            }

            ExamDetailState.EditExam -> {
                setTitle(R.string.update_exam)
            }

            ExamDetailState.NewExam -> {
                setTitle(R.string.new_exam)
            }
        }
    }

    private fun setupConfirmButton() = with(binding.btnConfirm) {
        when (viewModel.state) {
            ExamDetailState.SeeExam -> {
                isVisible = false
            }

            ExamDetailState.EditExam -> {
                text = "Atualizar"
                isVisible = true
                setOnClickListener { saveOrUpdateExam() }
            }

            ExamDetailState.NewExam -> {
                text = "Adicionar"
                isVisible = true
                setOnClickListener { saveOrUpdateExam() }
            }
        }
    }

    private fun saveOrUpdateExam() = with(binding.cpInputs) {
        updateExamField()
        val exam = currentExam
        println(exam)
    }

    private fun setupInputComponent() = with(binding.cpInputs) {
        setupExamField(viewModel.currentExamDTO)
        if (viewModel.state == ExamDetailState.SeeExam) {
            setInputEditable(false)
        } else {
            setInputEditable(true)
        }
    }
}