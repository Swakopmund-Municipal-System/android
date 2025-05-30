package com.example.swakopmundapp.data.model.weather

import com.google.gson.annotations.SerializedName

data class TideInfo(
    @SerializedName("height") val height: Double? = null,
    @SerializedName("time") val time: String? = null, // ISO 8601 format e.g., "2025-05-30T04:57:00+00:00"
    @SerializedName("type") val type: String? = null    // "high" or "low"
)

data class WeatherApiResponse(
    @SerializedName("coord") val coordinates: Coordinates? = null,
    @SerializedName("weather") val weatherInfo: List<WeatherDescription>? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("main") val mainStats: MainWeatherStats? = null,
    @SerializedName("visibility") val visibility: Int? = null,
    @SerializedName("wind") val wind: Wind? = null,
    @SerializedName("clouds") val clouds: Clouds? = null,
    @SerializedName("dt") val dateTime: Long? = null,
    @SerializedName("sys") val systemInfo: SystemInfo? = null,
    @SerializedName("timezone") val timezone: Int? = null,
    @SerializedName("id") val cityId: Int? = null,
    @SerializedName("name") val cityName: String? = null,
    @SerializedName("cod") val responseCode: Int? = null
)

data class Coordinates(
    @SerializedName("lon") val longitude: Double? = null,
    @SerializedName("lat") val latitude: Double? = null
)

data class WeatherDescription(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("main") val main: String? = null, // e.g. shit like, "Clear", "Clouds"
    @SerializedName("description") val description: String? = null, // e.g.pictures mhmm maybe, "clear sky"
    @SerializedName("icon") val icon: String? = null // e.g., "01d"
)

data class MainWeatherStats(
    @SerializedName("temp") val temperature: Double? = null, // In Kelvin by default from many APIs
    @SerializedName("feels_like") val feelsLike: Double? = null, // In Kelvin
    @SerializedName("temp_min") val tempMin: Double? = null,
    @SerializedName("temp_max") val tempMax: Double? = null,
    @SerializedName("pressure") val pressure: Int? = null,
    @SerializedName("humidity") val humidity: Int? = null,
    @SerializedName("sea_level") val seaLevelPressure: Int? = null,
    @SerializedName("grnd_level") val groundLevelPressure: Int? = null
)

data class Wind(
    @SerializedName("speed") val speed: Double? = null, // meter/sec
    @SerializedName("deg") val degree: Int? = null,
    @SerializedName("gust") val gust: Double? = null
)

data class Clouds(
    @SerializedName("all") val cloudiness: Int? = null // Percentage
)

data class SystemInfo(
    @SerializedName("country") val countryCode: String? = null,
    @SerializedName("sunrise") val sunriseTime: Long? = null, // Unix timestamp, UTC
    @SerializedName("sunset") val sunsetTime: Long? = null   // Unix timestamp, UTC
)