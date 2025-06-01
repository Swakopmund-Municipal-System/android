package com.example.swakopmundapp.data.network

import com.example.swakopmundapp.data.model.tourism.TourismActivity
import retrofit2.http.GET

interface TourismApiService {
    @GET("api/poi/places")
    suspend fun getPlaces(): List<TourismActivity>
}