package com.vald3nir.health_control.presentation.splash

import androidx.lifecycle.ViewModel
import com.vald3nir.auth.commons.use_cases.AuthUseCase
import com.vald3nir.auth.commons.use_cases.AuthUseCaseImpl

class SplashViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    fun hasUserLogged() = authUseCase.checkUserLogged()
}