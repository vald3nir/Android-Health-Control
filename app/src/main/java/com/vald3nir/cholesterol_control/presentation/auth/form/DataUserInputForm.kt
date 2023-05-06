package com.vald3nir.cholesterol_control.presentation.auth.form

data class DataUserInputForm(
    var emailError: String? = null,
    var nameError: String? = null,
    var passwordError: String? = null,
    var confirmPasswordError: String? = null,
)