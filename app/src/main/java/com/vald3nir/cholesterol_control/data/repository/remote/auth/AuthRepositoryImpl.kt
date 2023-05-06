package com.vald3nir.cholesterol_control.data.repository.remote.auth

import android.app.Activity
import android.content.Context
import com.vald3nir.cholesterol_control.data.dtos.ClientDTO
import com.vald3nir.cholesterol_control.data.dtos.LoginDTO
import com.vald3nir.core_repository.firebase.FirebaseAuthenticator
import com.vald3nir.core_repository.firebase.insertOrUpdate
import com.vald3nir.core_repository.storage.loadDataString
import com.vald3nir.core_repository.storage.saveDataString
import com.vald3nir.core_repository.toDTO

class AuthRepositoryImpl : AuthRepository {

    private val authenticator = FirebaseAuthenticator()
    private val LOGIN_KEY = "Login"
    private val USERS_PATH = "Usuários"

    override suspend fun login(
        activity: Activity,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val email: String = loginDTO.email ?: ""
        val password: String = loginDTO.password ?: ""
        authenticator.signInWithEmailAndPassword(activity, email, password, onSuccess, onError)
    }

    override suspend fun saveLoginData(
        context: Context?,
        loginDTO: LoginDTO
    ) {
        if (loginDTO.rememberLogin) {
            context?.saveDataString(LOGIN_KEY, loginDTO.toJson())
        } else {
            context?.saveDataString(LOGIN_KEY, null)
        }
    }

    override suspend fun loadLoginData(context: Context?): LoginDTO {
        val json = context?.loadDataString(LOGIN_KEY)
        return json.toDTO(LoginDTO::class.java)
    }

    override suspend fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        val firebaseAuthenticator = FirebaseAuthenticator()
        firebaseAuthenticator.createUserWithEmailAndPassword(
            activity = activity,
            email = email,
            password = password,
            onSuccess = onSuccess,
            onError = onError,
        )
    }

    override suspend fun registerClient(
        activity: Activity,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    ) {
        insertOrUpdate(
            path = USERS_PATH,
            dataJson = clientDTO.toJson().orEmpty(),
            onSuccess = { onSuccess.invoke() },
            onError
        )
    }
}