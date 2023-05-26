package com.vald3nir.auth.presentation

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vald3nir.auth.R
import com.vald3nir.auth.commons.forms.DataUserInputForm
import com.vald3nir.auth.commons.use_cases.AuthUseCase
import com.vald3nir.auth.data.dtos.LoginDTO
import com.vald3nir.core_ui.CoreViewModel
import com.vald3nir.core_ui.validators.isEmailValid
import com.vald3nir.core_ui.validators.isNameValid
import com.vald3nir.core_ui.validators.isPasswordValid
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authUseCase: AuthUseCase,
) : CoreViewModel() {

    private val _loginForm = MutableLiveData<DataUserInputForm>()
    val loginFormState: LiveData<DataUserInputForm> = _loginForm

    private val _registerForm = MutableLiveData<DataUserInputForm>()
    val registerFormState: LiveData<DataUserInputForm> = _registerForm

    private val _loginDTO = MutableLiveData<LoginDTO?>()
    val loginDTO: LiveData<LoginDTO?> = _loginDTO

    fun loadLoginData(context: Context?) {
        viewModelScope.launch {
            _loginDTO.value = authUseCase.loadLoginData(context)
        }
    }

    fun login(
        activity: Activity?,
        email: String,
        password: String,
        rememberLogin: Boolean,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        viewModelScope.launch {
            if (checkLoginData(activity, email, password)) {

                val loginDTO = LoginDTO(
                    email = email,
                    password = password,
                    rememberLogin = rememberLogin
                )

                authUseCase.login(activity, loginDTO,
                    onSuccess = {
                        saveLoginData(activity, loginDTO)
                        onSuccess.invoke()
                    },
                    onError = {
                        onError.invoke(it)
                    }
                )
            }
        }
    }

    private fun saveLoginData(context: Context?, loginDTO: LoginDTO) {
        viewModelScope.launch {
            authUseCase.saveLoginData(context, loginDTO)
        }
    }

    fun checkLoginData(context: Context?, email: String, password: String): Boolean {
        if (context == null) return false

        var isValid = true
        val dataUserInputForm = DataUserInputForm()

        if (!isEmailValid(email)) {
            isValid = false
            dataUserInputForm.emailError = context.getString(R.string.invalid_email)
        }

        if (!isPasswordValid(password)) {
            isValid = false
            dataUserInputForm.passwordError = context.getString(R.string.invalid_password)
        }

        if (!isValid) {
            _loginForm.value = dataUserInputForm
        }

        return isValid
    }

    fun registerNewUser(
        activity: Activity?,
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        if (checkRegisterData(
                context = activity,
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        ) {
            viewModelScope.launch {
                authUseCase.registerNewUser(
                    activity,
                    email = email,
                    password = password,
                    onSuccess = onSuccess,
                    onError = onError,
                )
            }
        }
    }

    private fun registerUserType(
        onSuccess: () -> Unit,
        activity: Activity?,
        name: String,
        email: String,
    ) {
//        viewModelScope.launch {
//            authUseCase.registerClient(
//                activity,
//                clientDTO = ClientDTO(
//                    name = name,
//                    userID = authUseCase.getUserID().orEmpty(),
//                    email = email,
//                ),
//                onSuccess = onSuccess,
//                onError = { //showError(it)
//                }
//            )
//        }
    }

    fun checkRegisterData(
        context: Context?,
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (context == null) return false
        var isValid = true
        val dataUserInputForm = DataUserInputForm()

        if (!isNameValid(name)) {
            isValid = false
            dataUserInputForm.nameError = context.getString(R.string.invalid_name)
        }

        if (!isEmailValid(email)) {
            isValid = false
            dataUserInputForm.emailError = context.getString(R.string.invalid_email)
        }

        if (!isPasswordValid(password)) {
            isValid = false
            dataUserInputForm.passwordError = context.getString(R.string.invalid_password)
        }

        if (!isPasswordValid(confirmPassword)) {
            isValid = false
            dataUserInputForm.confirmPasswordError = context.getString(R.string.invalid_password)
        }

        if (confirmPassword.isNotBlank() && password != confirmPassword) {
            isValid = false
            dataUserInputForm.confirmPasswordError =
                context.getString(R.string.passwords_not_equals)
        }

        if (!isValid) {
            _registerForm.value = dataUserInputForm
        }

        return isValid
    }
}