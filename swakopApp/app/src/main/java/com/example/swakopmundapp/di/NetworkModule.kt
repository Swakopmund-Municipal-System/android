package com.example.swakopmundapp.di

import com.example.swakopmundapp.data.network.OpenExchangeRatesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: PLEASE MOVE LOGIC TO SERVICE MODULE

val networkModule = module {

    // OkHttp Logging Interceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
        }
    }

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>()) // Add the logger
            // You can add other interceptors (e.g., for headers) here
            .build()
    }

    // Retrofit instance
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/") // Base URL for the API
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON
            .build()
    }

    // API Service (com.example.swakopmundapp.data.network.OpenExchangeRatesApi)
    single<OpenExchangeRatesApi> {
        get<Retrofit>().create(OpenExchangeRatesApi::class.java) // Create instance from Retrofit
    }
}