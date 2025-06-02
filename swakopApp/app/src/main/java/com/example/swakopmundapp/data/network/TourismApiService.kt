package com.example.swakopmundapp.data.network

import com.example.swakopmundapp.data.model.tourism.ApiActivity // New import
import com.example.swakopmundapp.data.model.tourism.TourismActivity // Existing import for getPlaces
import retrofit2.http.GET
import retrofit2.http.Query

interface TourismApiService {

    @GET("api/poi/places")
    suspend fun getPlaces(): List<TourismActivity>

    @GET("api/activities/search")
    suspend fun searchActivities(
        @Query("search_term") searchTerm: String?,
        @Query("sort_field") sortField: String = "id",
        @Query("sort_order") sortOrder: String = "desc",
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1
        // @Query("categories") categories: String? = null  // Optional: might add filtering later
    ): List<ApiActivity> // Expecting a list of ApiActivity
}