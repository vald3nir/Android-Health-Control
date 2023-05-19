package com.vald3nir.health_control.domain.common.extensions

// TO-DO mover para toolkit

fun Int?.formatString(): String {
    return this?.toString().orEmpty()
}

fun Float?.formatString(): String {
    return this?.toString().orEmpty()
}

fun Int.formatDecimal(): String {
    return if (this <= 9) "0$this" else "$this"
}

