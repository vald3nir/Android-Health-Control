package com.vald3nir.health_control.domain.common.diff

import androidx.recyclerview.widget.DiffUtil
import com.vald3nir.health_control.data.dto.ExamDTO

fun examDiffUtil(): DiffUtil.ItemCallback<ExamDTO> =
    object : DiffUtil.ItemCallback<ExamDTO>() {

        override fun areItemsTheSame(
            oldItem: ExamDTO,
            newItem: ExamDTO
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: ExamDTO,
            newItem: ExamDTO
        ): Boolean {
            return oldItem == newItem
        }
    }

