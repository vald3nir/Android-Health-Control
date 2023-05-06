package com.vald3nir.cholesterol_control

import android.app.Application
import com.vald3nir.cholesterol_control.data.repository.remote.auth.AuthRepository
import com.vald3nir.cholesterol_control.data.repository.remote.auth.AuthRepositoryImpl
import com.vald3nir.cholesterol_control.domain.use_cases.auth.AuthUseCase
import com.vald3nir.cholesterol_control.domain.use_cases.auth.AuthUseCaseImpl
import com.vald3nir.cholesterol_control.presentation.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@AppApplication)
            modules(
                listOf(
                    getAuthModule(),
                )
            )
        }
    }

    private fun getAuthModule() = module {
        viewModel { AuthViewModel(get()) }
        single<AuthRepository> { AuthRepositoryImpl() }
        single<AuthUseCase> { AuthUseCaseImpl(get()) }
    }
}