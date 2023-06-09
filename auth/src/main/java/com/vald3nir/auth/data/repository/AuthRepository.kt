package com.vald3nir.auth.data.repository

import android.app.Activity
import android.content.Context
import com.vald3nir.auth.data.dtos.LoginDTO

interface AuthRepository {

    fun getUserEmail(): String?

    suspend fun login(
        activity: Activity,
        loginDTO: LoginDTO,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )

    fun logout()

    suspend fun loadLoginData(context: Context?): LoginDTO?

    suspend fun saveLoginData(context: Context?, loginDTO: LoginDTO)

    suspend fun registerNewUser(
        activity: Activity,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (e: Exception?) -> Unit,
    )
}