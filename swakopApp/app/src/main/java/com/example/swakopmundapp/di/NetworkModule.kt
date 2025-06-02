package com.example.swakopmundapp.di

import com.example.swakopmundapp.BuildConfig
import com.example.swakopmundapp.data.network.EnvironmentalApiService
import com.example.swakopmundapp.data.network.OpenExchangeRatesApi
import com.example.swakopmundapp.data.network.TourismApiService
import com.example.swakopmundapp.data.network.WeatherApiService
import com.example.swakopmundapp.data.repository.EnvironmentalReportRepository // Keep if used
import com.example.swakopmundapp.data.repository.TourismRepository         // Keep if used
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//// --- API Keys ---
//const val SHARED_API_KEY = ha ha no key"
//const val SHARED_AUTH_TOKEN = "77ce ahh 1/3 of it"
//// --- End API Keys ---

val networkModule = module {

    // OkHttp Logging Interceptor (shared)
    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single<Interceptor>(named("AppAuthInterceptor")) {
        Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
//                .header("X-API-KEY", SHARED_API_KEY)
//                .header("Authorization", SHARED_AUTH_TOKEN)
                .header("X-API-KEY", BuildConfig.SHARED_API_KEY)
                .header("Authorization", BuildConfig.SHARED_AUTH_TOKEN)


            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    single<OkHttpClient>(named("RegularOkHttpClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<OkHttpClient>(named("AuthenticatedOkHttpClient")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("AppAuthInterceptor"))) // Use the updated interceptor
            .build()
    }

    // --- Currency Exchange API Setup ---
    single<Retrofit>(named("OpenExchangeRatesRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .client(get<OkHttpClient>(named("RegularOkHttpClient")))
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
            .client(get<OkHttpClient>(named("RegularOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<WeatherApiService> {
        get<Retrofit>(named("WeatherApiRetrofit")).create(WeatherApiService::class.java)
    }

    // --- Environmental Report API Setup ---
    single<Retrofit>(named("EnvironmentalApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/")
            .client(get<OkHttpClient>(named("AuthenticatedOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<EnvironmentalApiService> {
        get<Retrofit>(named("EnvironmentalApiRetrofit")).create(EnvironmentalApiService::class.java)
    }

    // --- Tourism API Setup ---
    single<Retrofit>(named("TourismApiRetrofit")) {
        Retrofit.Builder()
            .baseUrl("http://196.216.167.82/")
            .client(get<OkHttpClient>(named("AuthenticatedOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<TourismApiService> {
        get<Retrofit>(named("TourismApiRetrofit")).create(TourismApiService::class.java)
    }

    //TODO: move  Repository definitions in own 'repositoryModule.kt'
    single<EnvironmentalReportRepository> {
        EnvironmentalReportRepository(get())
    }
    single<TourismRepository> {
        TourismRepository(get())
    }
}