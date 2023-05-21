package com.vald3nir.auth.di

import com.vald3nir.auth.commons.use_cases.AuthUseCase
import com.vald3nir.auth.commons.use_cases.AuthUseCaseImpl
import com.vald3nir.auth.data.repository.AuthRepository
import com.vald3nir.auth.data.repository.AuthRepositoryImpl
import com.vald3nir.auth.presentation.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getAuthModule() = module {
    viewModel { AuthViewModel(get()) }
    single<AuthRepository> { AuthRepositoryImpl() }
    single<AuthUseCase> { AuthUseCaseImpl(get()) }
}