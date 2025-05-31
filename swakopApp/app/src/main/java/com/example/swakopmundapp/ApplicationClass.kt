package com.example.swakopmundapp

import android.app.Application
import com.example.swakopmundapp.di.networkModule
import com.example.swakopmundapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level // Koin's logger level enum

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            //chat said somthing like to  Log Koin activity to Logcat. Use Level.ERROR or Level.NONE for release builds.
            androidLogger(Level.DEBUG) // Or Level.INFO / Level.ERROR
            // Provide Android Application context to Koin
            androidContext(this@ApplicationClass)
            // Declare all Koin modules to be used by the application
            modules(listOf(networkModule, viewModelModule))
        }
    }
}