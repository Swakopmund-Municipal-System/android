package com.example.swakopmundapp.data.repository

import com.example.swakopmundapp.data.model.tourism.TourismActivity
import com.example.swakopmundapp.data.network.TourismApiService

class TourismRepository(
    private val apiService: TourismApiService
) {
    suspend fun getPlaces(): List<TourismActivity>{
        return apiService.getPlaces()
    }
}