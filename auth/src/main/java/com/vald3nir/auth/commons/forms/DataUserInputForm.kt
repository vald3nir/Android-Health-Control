package com.vald3nir.auth.commons.forms

data class DataUserInputForm(
    var emailError: String? = null,
    var nameError: String? = null,
    var passwordError: String? = null,
    var confirmPasswordError: String? = null,
)