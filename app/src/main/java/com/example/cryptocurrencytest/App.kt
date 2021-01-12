package com.example.cryptocurrencytest

import android.app.Application
import com.example.cryptocurrencytest.di.cryptocurrencyServiceModule
import com.example.cryptocurrencytest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(cryptocurrencyServiceModule, viewModelModule)
        }
    }
}