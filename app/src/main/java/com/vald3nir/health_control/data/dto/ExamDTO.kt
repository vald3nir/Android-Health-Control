package com.vald3nir.health_control.data.dto

import com.vald3nir.core_repository.BaseDTO
import com.vald3nir.core_ui.R

data class ExamDTO(
    var date: String?,
    var totalCholesterol: Int?,
    var HDL_D: Int?,
    var NOT_HDL: Int?,
    var LDL: Int?,
    var triglycerides: Int?,
    var uricAcid: Float?,
    var status: ExamStatus,
) : BaseDTO()

enum class ExamStatus(val text: String, val color: Int) {
    Normal(text = "Normal", color = R.color.green_light),
    Alert(text = "Alerta", color = R.color.orange),
    Risc(text = "Risco", color = R.color.red)
}
