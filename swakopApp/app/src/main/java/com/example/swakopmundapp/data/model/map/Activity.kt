package com.example.swakopmundapp.data.model.map

data class Activity(
    val id: String,
    val name: String,
    val description: String?,
    val latitude: Double,
    val longitude: Double,
    val type: String?,
    val duration: Int?, // Duration in minutes
    val priceRange: String?, // "budget", "mid-range", "luxury"
    val rating: Double?,
    val contact: Contact?,
    val location: String?
)

data class Contact(
    val phone: String?,
    val email: String?,
    val website: String?,
    val whatsapp: String?,
    val facebook: String?,
    val instagram: String?
)