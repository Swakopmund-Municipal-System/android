package com.example.swakopmundapp.data.network

import com.example.swakopmundapp.data.model.weather.TideInfo
import com.example.swakopmundapp.data.model.weather.WeatherApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApiService {
    @GET("weather/weather/")
    suspend fun getCurrentWeather(): Response<WeatherApiResponse>

    @GET("weather/weather/tide/")
    suspend fun getTideForecast(): Response<List<TideInfo>>
}