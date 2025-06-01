package com.example.swakopmundapp.service.client

import java.util.concurrent.TimeUnit

internal class OkHttpClient(
    private val interceptor: BasicAuthInterceptor
) {
    internal val instance = okhttp3.OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .callTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(interceptor)
        .build()
}