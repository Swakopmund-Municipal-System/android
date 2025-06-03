package com.example.swakopmundapp.data.repository

import android.util.Log
import com.example.swakopmundapp.BuildConfig
import com.example.swakopmundapp.data.model.map.Activity
import com.example.swakopmundapp.data.network.ActivitiesApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ActivitiesRepository {
    private val apiKey = BuildConfig.BACKEND_API_KEY
    private val baseUrl = "http://196.216.167.82/api/activities/"

    // Add auth repository
    private val authRepository = AuthRepository()

    init {
        Log.d("ActivitiesRepo", "Initializing ActivitiesRepository with Authentication")
        Log.d("ActivitiesRepo", "Base URL: $baseUrl")
        Log.d("ActivitiesRepo", "API Key: ${if (apiKey.isNotEmpty()) "Present (${apiKey.take(8)}...)" else "MISSING"}")
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createHttpClient())
        .build()

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()

                // Get current token (will be set after authentication)
                val userToken = authRepository.getCurrentToken()

                // Create request with proper authentication headers
                val authenticatedRequest = originalRequest.newBuilder()
                    .addHeader("X-API-KEY", apiKey)
                    .addHeader("X-RESOURCE", "activities")
                    .addHeader("X-SUB-RESOURCE", "search")
                    .addHeader("X-METHOD", "GET")
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .apply {
                        // Only add Authorization header if we have a token
                        if (userToken != null) {
                            addHeader("Authorization", "Token $userToken")
                        }
                    }
                    .build()
                Log.d("FULL_URL", "Request URL: ${authenticatedRequest.url}")


                Log.d("HTTP_REQUEST", "=== ACTIVITIES API REQUEST ===")
                Log.d("HTTP_REQUEST", "URL: ${authenticatedRequest.url}")
                Log.d("HTTP_REQUEST", "Headers:")
                authenticatedRequest.headers.forEach { (name, value) ->
                    val displayValue = if (name.contains("key", ignoreCase = true) || name.contains("auth", ignoreCase = true)) {
                        "${value.take(12)}..."
                    } else value
                    Log.d("HTTP_REQUEST", "  $name: $displayValue")
                }

                val response = chain.proceed(authenticatedRequest)

                Log.d("HTTP_RESPONSE", "=== ACTIVITIES API RESPONSE ===")
                Log.d("HTTP_RESPONSE", "Status: ${response.code} ${response.message}")
                Log.d("HTTP_RESPONSE", "Success: ${response.isSuccessful}")

                if (!response.isSuccessful) {
                    val errorBody = response.peekBody(1024).string()
                    Log.e("HTTP_RESPONSE", "Error Body: $errorBody")

                    // Specific authentication error logging
                    when (response.code) {
                        401 -> Log.e("AUTH_ERROR", "401 Unauthorized - Token may be expired, need to re-authenticate")
                        403 -> Log.e("AUTH_ERROR", "403 Forbidden - Check API key and required headers")
                        404 -> Log.e("AUTH_ERROR", "404 Not Found - Verify endpoint URL")
                        422 -> Log.e("AUTH_ERROR", "422 Unprocessable - Check request parameters")
                        500 -> Log.e("AUTH_ERROR", "500 Server Error - Backend service issue")
                    }
                }

                response
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val apiService = retrofit.create(ActivitiesApiService::class.java)

    suspend fun searchActivitiesByLocation(
        latitude: Double,
        longitude: Double,
        radius: Double = 10.0
    ): Result<List<Activity>> {
        return try {
            Log.d("API_CALL", "=== STARTING AUTHENTICATED ACTIVITIES SEARCH ===")

            // First, ensure we're authenticated
            if (!authRepository.isAuthenticated()) {
                Log.d("API_CALL", "No current token, attempting to authenticate...")

                authRepository.login().fold(
                    onSuccess = { token ->
                        Log.d("API_CALL", "Authentication successful, token: ${token.take(12)}...")
                    },
                    onFailure = { error ->
                        Log.e("API_CALL", "Authentication failed: ${error.message}")
                        return Result.failure(Exception("Authentication failed: ${error.message}"))
                    }
                )
            } else {
                Log.d("API_CALL", "Using existing authentication token")
            }

            Log.d("API_CALL", "Searching activities at coordinates: ($latitude, $longitude)")
            Log.d("API_CALL", "Search radius: $radius km")

            val response = apiService.searchActivitiesByLocation(latitude, longitude, radius)

            when {
                response.isSuccessful -> {
                    val activities = response.body() ?: emptyList()
                    Log.d("API_SUCCESS", "Found ${activities.size} activities")
                    if (activities.isNotEmpty()) {
                        Log.d("API_SUCCESS", "Sample activity: ${activities.first().name}")
                    }
                    Result.success(activities)
                }
                response.code() == 401 -> {
                    Log.e("API_ERROR", "Authentication failed - attempting to re-authenticate")

                    // Try to re-authenticate and retry
                    authRepository.login().fold(
                        onSuccess = {
                            Log.d("API_CALL", "Re-authentication successful, retrying request...")
                            // Retry the request with new token
                            searchActivitiesByLocation(latitude, longitude, radius)
                        },
                        onFailure = { error ->
                            Result.failure(Exception("Re-authentication failed: ${error.message}"))
                        }
                    )
                }
                response.code() == 403 -> {
                    Log.e("API_ERROR", "Access forbidden - check API permissions")
                    Result.failure(Exception("Access forbidden: Insufficient permissions"))
                }
                response.code() == 404 -> {
                    Log.e("API_ERROR", "Endpoint not found - check URL")
                    Result.failure(Exception("Endpoint not found: ${response.raw().request.url}"))
                }
                else -> {
                    val errorMsg = "HTTP ${response.code()}: ${response.message()}"
                    Log.e("API_ERROR", errorMsg)
                    Result.failure(Exception(errorMsg))
                }
            }
        } catch (e: java.net.UnknownHostException) {
            Log.e("NETWORK_ERROR", "Cannot reach server: ${e.message}")
            Result.failure(Exception("Network error: Cannot reach server"))
        } catch (e: java.net.SocketTimeoutException) {
            Log.e("NETWORK_ERROR", "Request timeout: ${e.message}")
            Result.failure(Exception("Request timeout"))
        } catch (e: Exception) {
            Log.e("API_EXCEPTION", "Unexpected error: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getActivity(id: String): Result<Activity> {
        return try {
            // Ensure authentication
            if (!authRepository.isAuthenticated()) {
                authRepository.login().fold(
                    onSuccess = { },
                    onFailure = { error ->
                        return Result.failure(Exception("Authentication failed: ${error.message}"))
                    }
                )
            }

            Log.d("API_CALL", "Getting activity: $id")
            val response = apiService.getActivity(id)

            if (response.isSuccessful) {
                response.body()?.let { activity ->
                    Log.d("API_SUCCESS", "Retrieved activity: ${activity.name}")
                    Result.success(activity)
                } ?: Result.failure(Exception("Activity not found"))
            } else {
                val errorMsg = "Failed to get activity: HTTP ${response.code()}"
                Log.e("API_ERROR", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("API_EXCEPTION", "Error getting activity: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun searchActivities(
        query: String? = null,
        type: String? = null,
        priceRange: String? = null
    ): Result<List<Activity>> {
        return try {
            // Ensure authentication
            if (!authRepository.isAuthenticated()) {
                authRepository.login().fold(
                    onSuccess = { },
                    onFailure = { error ->
                        return Result.failure(Exception("Authentication failed: ${error.message}"))
                    }
                )
            }

            Log.d("API_CALL", "Searching activities with query: '$query', type: '$type', priceRange: '$priceRange'")
            val response = apiService.searchActivities(query, type, priceRange)

            if (response.isSuccessful) {
                val activities = response.body() ?: emptyList()
                Log.d("API_SUCCESS", "Search found ${activities.size} activities")
                Result.success(activities)
            } else {
                val errorMsg = "Search failed: HTTP ${response.code()}"
                Log.e("API_ERROR", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e("API_EXCEPTION", "Exception during search: ${e.message}")
            Result.failure(e)
        }
    }
}