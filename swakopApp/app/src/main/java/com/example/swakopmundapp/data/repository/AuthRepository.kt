package com.example.swakopmundapp.data.repository

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// Data classes for authentication
data class LoginRequest(
    val username: String,  // Note: API expects "username" not "email"
    val password: String
)

data class LoginResponse(
    val token: String?,
    val user: UserInfo?
)

data class UserInfo(
    val id: String?,
    val username: String?,
    val email: String?
)

// Auth API interface
interface AuthApiService {
    @POST("api/auth/user/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest,
        @Header("X-CSRFTOKEN") csrfToken: String? = null
    ): Response<LoginResponse>
}

class AuthRepository {
    private val baseUrl = "http://196.216.167.82/"

    // User credentials from your example
    private val username = "ralph@ralph.com"
    private val password = "WXQhgQt5Brpii2p"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createHttpClient())
        .build()

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()

                Log.d("AUTH_REQUEST", "=== AUTHENTICATION REQUEST ===")
                Log.d("AUTH_REQUEST", "URL: ${request.url}")
                Log.d("AUTH_REQUEST", "Method: ${request.method}")
                Log.d("AUTH_REQUEST", "Headers:")
                request.headers.forEach { (name, value) ->
                    Log.d("AUTH_REQUEST", "  $name: $value")
                }

                val response = chain.proceed(request)

                Log.d("AUTH_RESPONSE", "=== AUTHENTICATION RESPONSE ===")
                Log.d("AUTH_RESPONSE", "Status: ${response.code} ${response.message}")

                if (!response.isSuccessful) {
                    val errorBody = response.peekBody(1024).string()
                    Log.e("AUTH_RESPONSE", "Error Body: $errorBody")
                }

                response
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val authService = retrofit.create(AuthApiService::class.java)

    // Store the current token
    private var currentToken: String? = null

    suspend fun login(): Result<String> {
        return try {
            Log.d("AUTH", "Attempting to login with username: $username")

            val loginRequest = LoginRequest(
                username = username,
                password = password
            )

            val response = authService.login(loginRequest)

            when {
                response.isSuccessful -> {
                    val loginResponse = response.body()
                    val token = loginResponse?.token

                    if (token != null) {
                        currentToken = token
                        Log.d("AUTH", "Login successful! Token: ${token.take(12)}...")
                        Result.success(token)
                    } else {
                        Log.e("AUTH", "Login response missing token")
                        Result.failure(Exception("Login response missing token"))
                    }
                }
                response.code() == 400 -> {
                    Log.e("AUTH", "Bad request - check credentials format")
                    Result.failure(Exception("Bad request: Check username/password format"))
                }
                response.code() == 401 -> {
                    Log.e("AUTH", "Invalid credentials")
                    Result.failure(Exception("Invalid credentials"))
                }
                else -> {
                    val errorMsg = "Login failed: HTTP ${response.code()}"
                    Log.e("AUTH", errorMsg)
                    Result.failure(Exception(errorMsg))
                }
            }
        } catch (e: Exception) {
            Log.e("AUTH", "Login exception: ${e.message}")
            Result.failure(e)
        }
    }

    fun getCurrentToken(): String? {
        return currentToken
    }

    fun isAuthenticated(): Boolean {
        return currentToken != null
    }
}