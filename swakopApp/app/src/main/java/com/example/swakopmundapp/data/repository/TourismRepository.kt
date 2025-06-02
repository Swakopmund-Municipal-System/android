package com.example.swakopmundapp.data.repository

import com.example.swakopmundapp.data.model.tourism.TourismActivity
import com.example.swakopmundapp.data.model.tourism.ApiActivity
import com.example.swakopmundapp.data.network.TourismApiService
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class TourismRepository(
    private val apiService: TourismApiService
) {
    suspend fun getPlaces(): List<TourismActivity> {
        return apiService.getPlaces()
    }

    suspend fun searchActivities(searchTerm: String?): List<ApiActivity> = withContext(Dispatchers.IO) {
        return@withContext apiService.searchActivities(
            searchTerm = searchTerm,
            sortField = "name",
            sortOrder = "asc"
            // TODO: adjust limit and page for implement pagination
        )
    }
}
