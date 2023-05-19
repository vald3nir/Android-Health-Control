package com.vald3nir.health_control.di

import com.vald3nir.health_control.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun homeModule() = module {
    viewModel { HomeViewModel() }
//            single<AuthRepository> { AuthRepositoryImpl(get()) }
//            single<AuthUseCase> { AuthUseCaseImpl(get()) }
}