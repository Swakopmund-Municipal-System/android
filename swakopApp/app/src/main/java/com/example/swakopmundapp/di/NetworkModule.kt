package com.example.swakopmundapp.di

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

// --- API Keys - IMPORTANT: Store these securely, not hardcoded in production! ---
const val SHARED_API_KEY = "77ce29bc24154358a988bc898f818bd1"
const val SHARED_AUTH_TOKEN = "77ce29bc24154358a988bc898f818bd1"
// --- End API Keys ---

val networkModule = module {

    // OkHttp Logging Interceptor (shared)
    single<HttpLoggingInterceptor> { // Explicit type for clarity
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // UPDATED Authentication Interceptor
    // This will be used for APIs that require X-API-KEY and Authorization headers
    single<Interceptor>(named("AppAuthInterceptor")) { // Renamed for clarity
        Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                // Add your API key and auth token here
                .header("X-API-KEY", SHARED_API_KEY)
                .header("Authorization", SHARED_AUTH_TOKEN)
            // Removed Content-Type as it's often not needed for GET and can be added per-request if required for POST/PUT

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    // Regular OkHttpClient (for APIs NOT requiring the AppAuthInterceptor)
    single<OkHttpClient>(named("RegularOkHttpClient")) { // Give it a distinct name
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // OkHttpClient WITH AppAuthInterceptor (for APIs like Environmental, Tourism)
    single<OkHttpClient>(named("AuthenticatedOkHttpClient")) { // Give it a distinct name
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>(named("AppAuthInterceptor"))) // Use the updated interceptor
            .build()
    }

    // --- Currency Exchange API Setup ---
    single<Retrofit>(named("OpenExchangeRatesRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            // Use RegularOkHttpClient if this API doesn't need the shared X-API-KEY/Auth
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
            // Use RegularOkHttpClient if this API doesn't need the shared X-API-KEY/Auth
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
            // Use the client WITH the AppAuthInterceptor
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
            .baseUrl("http://196.216.167.82/") // Same base URL
            // CRITICAL: Use the client WITH the AppAuthInterceptor
            .client(get<OkHttpClient>(named("AuthenticatedOkHttpClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<TourismApiService> {
        get<Retrofit>(named("TourismApiRetrofit")).create(TourismApiService::class.java)
    }

    // Repository definitions should ideally be in their own 'repositoryModule.kt'
    // but if you prefer them here for now:
    single<EnvironmentalReportRepository> {
        EnvironmentalReportRepository(get()) // Depends on EnvironmentalApiService
    }
    single<TourismRepository> {
        TourismRepository(get()) // Depends on TourismApiService
    }
}