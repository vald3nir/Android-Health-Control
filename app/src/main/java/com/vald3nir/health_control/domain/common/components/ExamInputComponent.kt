package com.vald3nir.health_control.domain.common.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.vald3nir.health_control.data.dto.ExamDTO
import com.vald3nir.health_control.data.dto.ExamStatus
import com.vald3nir.health_control.databinding.ComponentExamInputBinding
import com.vald3nir.health_control.domain.common.extensions.*
import java.util.Calendar

class ExamInputComponent : LinearLayoutCompat {

    var currentExam: ExamDTO? = null
    private val binding by lazy {
        ComponentExamInputBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    init {
        orientation = VERTICAL
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )


    @SuppressLint("SetTextI18n")
    fun setupExamField(exam: ExamDTO?) = with(binding) {
        currentExam = exam

        edtDate.apply {
            setText(exam?.date)
            setOnClickListener {
                showDatePickerDialog { _, year, monthOfYear, dayOfMonth ->
                    setText("${dayOfMonth.formatDecimal()}/${(monthOfYear + 1).formatDecimal()}/${year}")
                }
            }
        }

        edtTotalCholesterol.setText(exam?.totalCholesterol.formatString())
        edtHdl.setText(exam?.HDL_D.formatString())
        edtNotHdl.setText(exam?.NOT_HDL.formatString())
        edtLdl.setText(exam?.LDL.formatString())
        edtTriglycerides.setText(exam?.triglycerides.formatString())
        edtUricAcid.setText(exam?.uricAcid.formatString())
        validateExamField()
    }

    fun updateExamField() = with(binding) {
        currentExam?.date = edtDate.text.toString()
        currentExam?.totalCholesterol = edtTotalCholesterol.toInt()
        currentExam?.HDL_D = edtHdl.toInt()
        currentExam?.NOT_HDL = edtNotHdl.toInt()
        currentExam?.LDL = edtLdl.toInt()
        currentExam?.triglycerides = edtTriglycerides.toInt()
        currentExam?.uricAcid = edtUricAcid.toFloat()
        validateExamField()
    }

    private fun validateExamField() = with(binding) {

        var numberWarnings = 0
        currentExam?.date = edtDate.text.toString()

        if (edtTotalCholesterol.toIntOrZero() >= 190) {
            numberWarnings += 1
            edtTotalCholesterolLayout.error = "O valor apropriado é ser menor que 190"
        } else {
            edtTotalCholesterolLayout.error = null
        }

        if (edtHdl.toIntOrZero() in 1..39) {
            numberWarnings += 1
            edtHdlLayout.error = "O valor apropriado é ser maior ou igual a 40"
        } else {
            edtHdlLayout.error = null
        }

        if (edtNotHdl.toIntOrZero() >= 160) {
            numberWarnings += 1
            edtNotHdlLayout.error = "O valor apropriado é ser menor que 160"
        } else {
            edtNotHdlLayout.error = null
        }

        if (edtLdl.toIntOrZero() >= 130) {
            numberWarnings += 1
            edtLdlLayout.error = "O valor apropriado é ser menor que 130"
        } else {
            edtLdlLayout.error = null
        }

        if (edtTriglycerides.toIntOrZero() > 150) {
            numberWarnings += 1
            edtTriglyceridesLayout.error = "O valor apropriado é ser menor ou igual a 150"
        } else {
            edtTriglyceridesLayout.error = null
        }

        if (edtUricAcid.toFloatOrZero() > 7.2f) {
            numberWarnings += 1
            edtUricAcidLayout.error = "O valor apropriado é ser menor ou igual a 7.2"
        } else {
            edtUricAcidLayout.error = null
        }

        val percentage = (numberWarnings / 6f) * 100
        currentExam?.status = when {
            percentage > 70 -> ExamStatus.Risc
            percentage >= 30 -> ExamStatus.Alert
            else -> ExamStatus.Normal
        }
    }

    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        val cldr: Calendar = Calendar.getInstance()
        val day: Int = cldr.get(Calendar.DAY_OF_MONTH)
        val month: Int = cldr.get(Calendar.MONTH)
        val year: Int = cldr.get(Calendar.YEAR)
        val picker = DatePickerDialog(context, listener, year, month, day)
        picker.show()
    }

    fun setInputEditable(enable: Boolean) = with(binding) {
        edtDate.isEnabled = enable
        edtTotalCholesterol.isEnabled = enable
        edtHdl.isEnabled = enable
        edtNotHdl.isEnabled = enable
        edtLdl.isEnabled = enable
        edtTriglycerides.isEnabled = enable
        edtUricAcid.isEnabled = enable
    }

}