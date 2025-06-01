package com.example.swakopmundapp.di

import com.example.swakopmundapp.data.network.EnvironmentalApiService
import com.example.swakopmundapp.data.network.OpenExchangeRatesApi
import com.example.swakopmundapp.data.network.WeatherApiService // <-- Add this import
import com.example.swakopmundapp.data.repository.EnvironmentalReportRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named // <-- Add this for named instances if you go that route
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// const val OPEN_EXCHANGE_RATES_RETROFIT = "OpenExchangeRatesRetrofit"
// const val WEATHER_API_RETROFIT = "WeatherApiRetrofit"

val networkModule = module {

    // OkHttp Logging Interceptor (shared)
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // OkHttpClient (shared)
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // --- Currency Exchange API Setup ---
    single<Retrofit>(named("OpenExchangeRatesRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .client(get<OkHttpClient>()) // Use the shared OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OpenExchangeRatesApi> {
        // Get the named Retrofit instance
        get<Retrofit>(named("OpenExchangeRatesRetrofit")).create(OpenExchangeRatesApi::class.java)
    }

    // --- Weather API Setup ---
    single<Retrofit>(named("WeatherApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/api/")
            .client(get<OkHttpClient>())      // Use the shared OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<WeatherApiService> {
        // Get the named Retrofit instance for Weather
        get<Retrofit>(named("WeatherApiRetrofit")).create(WeatherApiService::class.java)
    }

    single<Retrofit>(named("EnvironmentalApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/") // Use your base URL
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<EnvironmentalApiService> {
        get<Retrofit>(named("EnvironmentalApiRetrofit")).create(EnvironmentalApiService::class.java)
    }

    single<EnvironmentalReportRepository> {
        EnvironmentalReportRepository(get())
    }
}