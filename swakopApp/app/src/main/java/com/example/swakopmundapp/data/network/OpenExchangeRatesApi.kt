package com.example.swakopmundapp.data.network

import com.example.swakopmundapp.data.model.currency.ExchangeRatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeRatesApi {
    @GET("latest.json")  // This hits the "latest.json" endpoint
    suspend fun getLatestRates(
        @Query("app_id") appId: String,  // should i leave this here..mhmm maybe
//        @Query("base") base: String = "NAD"   turns out to use this shit i need to pay like niggah tf
    ): Response<ExchangeRatesResponse>
}

