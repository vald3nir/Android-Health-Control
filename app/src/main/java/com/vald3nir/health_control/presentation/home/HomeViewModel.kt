package com.vald3nir.health_control.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vald3nir.auth.commons.use_cases.AuthUseCase
import com.vald3nir.health_control.data.dto.ExamDTO
import com.vald3nir.health_control.data.dto.ExamStatus
import com.vald3nir.health_control.data.enums.ExamDetailState

class HomeViewModel(private val authUseCase: AuthUseCase) : ViewModel() {


    private val _exams = MutableLiveData<List<ExamDTO>>()
    val exams: LiveData<List<ExamDTO>> = _exams
    var currentExamDTO: ExamDTO? = null
    var state: ExamDetailState = ExamDetailState.SeeExam

    fun enableEditExamDetailState() {
        state = ExamDetailState.EditExam
    }

    fun loadExams(
//        onSuccess: (exams: List<ExamDTO>) -> Unit
    ) {

        _exams.value = listOf(
            ExamDTO(
                date = "12/12/2020",
                totalCholesterol = 251,
                HDL_D = 0,
                NOT_HDL = 0,
                LDL = 0,
                triglycerides = 156,
                uricAcid = null,
                status = ExamStatus.Risc
            ),
            ExamDTO(
                date = "29/06/2021",
                totalCholesterol = 239,
                HDL_D = 37,
                NOT_HDL = 202,
                LDL = 165,
                triglycerides = 207,
                uricAcid = null,
                status = ExamStatus.Normal
            ),
            ExamDTO(
                date = "25/10/2021",
                totalCholesterol = 142,
                HDL_D = 43,
                NOT_HDL = 99,
                LDL = 78,
                triglycerides = 128,
                uricAcid = null,
                status = ExamStatus.Alert
            ),
            ExamDTO(
                date = "08/02/2022",
                totalCholesterol = 192,
                HDL_D = 42,
                NOT_HDL = 150,
                LDL = 117,
                triglycerides = 211,
                uricAcid = null,
                status = ExamStatus.Alert
            ),
            ExamDTO(
                date = "30/09/2022",
                totalCholesterol = 174,
                HDL_D = 39,
                NOT_HDL = 135,
                LDL = 108,
                triglycerides = 152,
                uricAcid = 7.9f,
                status = ExamStatus.Normal
            ),
            ExamDTO(
                date = "23/01/2023",
                totalCholesterol = 156,
                HDL_D = 39,
                NOT_HDL = 117,
                LDL = 96,
                triglycerides = 118,
                uricAcid = 6.6f,
                status = ExamStatus.Normal
            ),
            ExamDTO(
                date = "25/05/2023",
                totalCholesterol = 207,
                HDL_D = 30,
                NOT_HDL = 177,
                LDL = 145,
                triglycerides = 80,
                uricAcid = 6.3f,
                status = ExamStatus.Normal
            )
        )
    }

    fun logout() {
        authUseCase.logout()
    }

}