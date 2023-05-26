package com.vald3nir.auth.commons.use_cases

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vald3nir.auth.data.dtos.ClientDTO
import com.vald3nir.auth.data.dtos.LoginDTO
import com.vald3nir.auth.data.repository.AuthRepository

class AuthUseCaseImpl(
    private val repository: AuthRepository,
) : AuthUseCase {


    override fun checkUserLogged(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun getUserEmail() = repository.getUserEmail()

    override suspend fun login(
        activity: Activity?,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    ) {
        if (activity != null) {
            repository.login(
                activity = activity,
                loginDTO = loginDTO,
                onSuccess = onSuccess,
                onError = onError
            )
        } else {
            onError.invoke(Exception("Activity null"))
        }
    }

    override fun logout() {
        repository.logout()
    }

    override suspend fun loadLoginData(context: Context?): LoginDTO? {
        return repository.loadLoginData(context)
    }

    override suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO) {
        repository.saveLoginData(context, loginDTO)
    }

    override suspend fun registerNewUser(
        activity: Activity?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        activity?.let {
            repository.registerNewUser(
                activity = it,
                email = email,
                password = password,
                onSuccess = onSuccess,
                onError = onError,
            )
        }
    }

    override suspend fun registerClient(
        activity: Activity?,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
    }
}