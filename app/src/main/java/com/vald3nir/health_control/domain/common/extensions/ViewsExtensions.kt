package com.vald3nir.health_control.domain.common.extensions

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText


fun TextView.setTextColorInt(corID: Int) {
    setTextColor(ContextCompat.getColor(context, corID))
}

fun TextInputEditText?.toInt(): Int? {
    return if (this?.text.isNullOrBlank()) null
    else this?.text.toString().toInt()
}

fun TextInputEditText?.toFloat(): Float? {
    return if (this?.text.isNullOrBlank()) null
    else this?.text.toString().toFloat()
}

fun TextInputEditText?.toIntOrZero(): Int {
    return toInt() ?: 0
}

fun TextInputEditText?.toFloatOrZero(): Float {
    return toFloat() ?: 0.0f
}