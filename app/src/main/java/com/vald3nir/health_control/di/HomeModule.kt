package com.vald3nir.health_control.di

import com.vald3nir.health_control.presentation.home.HomeViewModel
import com.vald3nir.health_control.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun appModule() = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}