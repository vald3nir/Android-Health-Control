package com.vald3nir.health_control

import android.app.Application
import com.vald3nir.auth.di.getAuthModule
import com.vald3nir.health_control.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@AppApplication)
            modules(listOf(homeModule(), getAuthModule()))
        }
    }
}