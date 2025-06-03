package com.example.swakopmundapp.data.network




import com.example.swakopmundapp.data.model.map.Activity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivitiesApiService {

    @GET("search/location")
    suspend fun searchActivitiesByLocation(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("radius") radius: Double
    ): Response<List<Activity>>

    @GET("search")
    suspend fun searchActivities(
        @Query("q") query: String? = null,
        @Query("type") type: String? = null,
        @Query("priceRange") priceRange: String? = null
    ): Response<List<Activity>>

    @GET("{id}")
    suspend fun getActivity(
        @Path("id") id: String
    ): Response<Activity>
}