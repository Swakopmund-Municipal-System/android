package com.example.swakopmundapp.di

import com.example.swakopmundapp.data.network.EnvironmentalApiService
import com.example.swakopmundapp.data.network.OpenExchangeRatesApi
import com.example.swakopmundapp.data.network.TourismApiService
import com.example.swakopmundapp.data.network.WeatherApiService
import com.example.swakopmundapp.data.repository.EnvironmentalReportRepository
import com.example.swakopmundapp.data.repository.TourismRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    // OkHttp Logging Interceptor (shared)
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Authentication Interceptor for Environmental API
    single(named("AuthInterceptor")) {
        Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
            // Add your API key or authentication token here
            // .header("Authorization", "Bearer YOUR_API_KEY")
            // .header("X-API-Key", "YOUR_API_KEY")

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    // Regular OkHttpClient (shared for currency and weather)
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // OkHttpClient with Auth for Environmental API
    single(named("AuthenticatedClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("AuthInterceptor")))
            .build()
    }

    // --- Currency Exchange API Setup ---
    single<Retrofit>(named("OpenExchangeRatesRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OpenExchangeRatesApi> {
        get<Retrofit>(named("OpenExchangeRatesRetrofit")).create(OpenExchangeRatesApi::class.java)
    }

    // --- Weather API Setup ---
    single<Retrofit>(named("WeatherApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/api/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<WeatherApiService> {
        get<Retrofit>(named("WeatherApiRetrofit")).create(WeatherApiService::class.java)
    }

    // --- Environmental Report API Setup ---
    single<Retrofit>(named("EnvironmentalApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/") // Base URL for environmental API
            .client(get<OkHttpClient>(named("AuthenticatedClient"))) // Use authenticated client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<EnvironmentalApiService> {
        get<Retrofit>(named("EnvironmentalApiRetrofit")).create(EnvironmentalApiService::class.java)
    }

    // Environmental Report Repository
    single<EnvironmentalReportRepository> {
        EnvironmentalReportRepository(get())
    }

    // --- Tourism API Setup ---
    single<Retrofit>(named("TourismApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/") // Same base URL as your environmental API
            .client(get<OkHttpClient>(named("AuthenticatedClient"))) // Using authenticated client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<TourismApiService> {
        get<Retrofit>(named("TourismApiRetrofit")).create(TourismApiService::class.java)
    }

    // Tourism Repository
    single<TourismRepository> {
        TourismRepository(get())
    }
}