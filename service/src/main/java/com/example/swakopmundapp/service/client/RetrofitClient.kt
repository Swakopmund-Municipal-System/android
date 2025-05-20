package com.example.swakopmundapp.service.client

import com.example.swakopmundapp.service.api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RetrofitClient(private val okHttpClient: OkHttpClient) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.instance)
        .build()

    val instance: Api = retrofit.create(Api::class.java)
}