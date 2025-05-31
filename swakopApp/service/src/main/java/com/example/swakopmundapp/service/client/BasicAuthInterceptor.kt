package com.example.swakopmundapp.service.client

import okhttp3.Interceptor
import okhttp3.Response

const val API_KEY = ""

internal class BasicAuthInterceptor: Interceptor {

    @Volatile
    private var token: String = ""
    private var apiKey: String = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun setApiKey(apiKey: String) {
        this.apiKey = apiKey
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.toString()

        // Skip authentication for login and signup endpoints
        if (url.contains("/auth/user/login/") || url.contains("/auth/user/sign-up/")) {
            return chain.proceed(request)
        }

        // For other endpoints, add auth headers
        val requestBuilder = request.newBuilder()

        request = request.newBuilder()
            .header("Authorization", "Token $token")
            .header("ApiKey", API_KEY)
            .build()

        request = requestBuilder.build()
        return chain.proceed(request)
    }
}