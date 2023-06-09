package com.vald3nir.auth.commons.use_cases

import android.app.Activity
import android.content.Context
import com.vald3nir.auth.data.dtos.ClientDTO
import com.vald3nir.auth.data.dtos.LoginDTO

interface AuthUseCase {

    fun checkUserLogged(): Boolean

    fun getUserEmail(): String?

    suspend fun login(
        activity: Activity?,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    fun logout()

    suspend fun loadLoginData(context: Context?): LoginDTO?

    suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO)

    suspend fun registerNewUser(
        activity: Activity?,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    suspend fun registerClient(
        activity: Activity?,
        clientDTO: ClientDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit
    )
}