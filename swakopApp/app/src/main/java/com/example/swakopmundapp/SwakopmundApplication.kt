package com.example.swakopmundapp

import android.app.Application
import com.example.swakopmundapp.di.networkModule
import com.example.swakopmundapp.koin.repositoryModule
import com.example.swakopmundapp.koin.viewModelModule
import com.example.swakopmundapp.service.serviceModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SwakopmundApplication : Application() {

    companion object {
        lateinit var instance: SwakopmundApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        val koin = startKoin {
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule,
                    serviceModule
                )
            )
        }
        koin.allowOverride(true)
    }
}