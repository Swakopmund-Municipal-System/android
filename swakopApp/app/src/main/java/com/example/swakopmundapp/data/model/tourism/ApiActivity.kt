package com.example.swakopmundapp.data.model.tourism

import com.google.gson.annotations.SerializedName

data class ApiActivity(
    val id: String,
    val name: String,
    val description: String?,
    val address: String?,
    @SerializedName("hero_image_url")
    val heroImage: String?,
    @SerializedName("booking_url")
    val bookingUrl: String?,
    val type: Int, // FESTIVAL = 0 RECREATIONAL = 1
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("image_id")
    val imageId: Int?,

){
    val constructedImageUrl: String?
        get() = imageId?.let { "http://196.216.167.82/api/activities/images/$it" }
}