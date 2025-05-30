package com.example.swakopmundapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

//shandie for logging
import android.util.Log

import com.example.swakopmundapp.data.model.weather.TideInfo
import com.example.swakopmundapp.data.model.weather.WeatherApiResponse
import com.example.swakopmundapp.data.network.WeatherApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
// Add these imports:
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale // ADDED IMPORT FOR LOCALE
import kotlin.math.roundToInt

// New data class for processed tide info
data class ProcessedTideInfo(
    val formattedTime: String,
    val type: String, // "High" or "Low"
    val height: String // e.g., "0.49m"
)

// Represents the UI state for the Weather Screen
data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherData: ProcessedWeatherData? = null,
    val tideForecast: List<ProcessedTideInfo>? = null, // <-- ADDED for tide forecast
    val error: String? = null,
    val lastUpdated: String? = null,
    val isDayTime: Boolean = true // Default to day
)

// Simplified data structure for the UI, derived from WeatherApiResponse
data class ProcessedWeatherData(
    val cityName: String,
    val temperature: String, // e.g., "20°C"
    val condition: String,   // e.g., "Clear"
    val conditionDescription: String, //e.g. "clear sky"
    val humidity: String,    // e.g., "54%"
    val wind: String,        // e.g., "1.3 m/s"
    val feelsLike: String,   // e.g., "19°C"
    val iconCode: String? = null // For fetching weather icon
)

class WeatherViewModel(
    private val weatherApiService: WeatherApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState(isLoading = true))
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    // Formatter for tide time display (e.g., "04:57 AM")
    // Consider changing to "HH:mm" and using Locale if you want 24-hour format consistently
    private val tideTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US) // CHANGED to HH:mm with Locale.US

    init {
        fetchAllWeatherData() // Renamed to reflect fetching all data
    }

    // Renamed and modified to fetch both current weather and tide data
    fun fetchAllWeatherData() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                // Fetch current weather and tide forecast concurrently
                coroutineScope {
                    val currentWeatherDeferred = async { weatherApiService.getCurrentWeather() }
                    val tideForecastDeferred = async { weatherApiService.getTideForecast() } // <-- Fetch tide data

                    val currentWeatherResponse = currentWeatherDeferred.await()
                    val tideForecastResponse = tideForecastDeferred.await() // <-- Await tide data

                    var currentWeatherData: ProcessedWeatherData? = null
                    var isDay = true // Default
                    var lastUpdateTime: String? = null
                    var apiError: String? = null

                    // Process current weather
                    if (currentWeatherResponse.isSuccessful) {
                        val apiData = currentWeatherResponse.body()
                        if (apiData != null) {
                            currentWeatherData = processApiResponse(apiData)
                            lastUpdateTime = LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
                            )
                            val nowHour = LocalDateTime.now().hour
                            isDay = apiData.systemInfo?.sunriseTime?.let { sunrise ->
                                apiData.systemInfo.sunsetTime?.let { sunset ->
                                    val currentTimeSeconds = Instant.now().epochSecond
                                    currentTimeSeconds in sunrise until sunset
                                }
                            } ?: (nowHour in 6..18)
                        } else {
                            Log.w("WeatherViewModelDebug", "Current weather API response body is null.")
                            apiError = "Empty response from current weather API."
                        }
                    } else {
                        Log.e("WeatherViewModelDebug", "Current Weather API Error: ${currentWeatherResponse.code()} - ${currentWeatherResponse.message()}")
                        apiError = "Current Weather API Error: ${currentWeatherResponse.code()} - ${currentWeatherResponse.message()}"
                    }

                    // Process tide forecast
                    var processedTides: List<ProcessedTideInfo>? = null
                    if (tideForecastResponse.isSuccessful) {
                        val tideApiData = tideForecastResponse.body()
                        Log.d("WeatherViewModelDebug", "Raw Tide API Data: $tideApiData") // LOG RAW TIDE DATA
                        if (tideApiData != null) {
                            processedTides = processTideForecast(tideApiData) // <-- Process tide data
                            Log.d("WeatherViewModelDebug", "Processed Tides for UI: $processedTides") // LOG PROCESSED TIDE DATA
                        } else {
                            Log.w("WeatherViewModelDebug", "Tide API response body is null.")
                            apiError = (apiError?.plus("\n") ?: "") + "Empty response from tide forecast API."
                        }
                    } else {
                        Log.e("WeatherViewModelDebug", "Tide Forecast API Error: ${tideForecastResponse.code()} - ${tideForecastResponse.message()}")
                        apiError = (apiError?.plus("\n") ?: "") + "Tide Forecast API Error: ${tideForecastResponse.code()} - ${tideForecastResponse.message()}"
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            weatherData = currentWeatherData,
                            tideForecast = processedTides, // <-- Update UI state with tides
                            lastUpdated = lastUpdateTime,
                            isDayTime = isDay,
                            error = apiError
                        )
                    }
                }
            } catch (e: IOException) {
                Log.e("WeatherViewModelDebug", "Network error: ${e.message}", e)
                _uiState.update { it.copy(isLoading = false, error = "Network error. Please check connection.") }
            } catch (e: Exception) {
                Log.e("WeatherViewModelDebug", "An unexpected error occurred: ${e.message}", e)
                _uiState.update { it.copy(isLoading = false, error = "An unexpected error occurred: ${e.message}") }
            }
        }
    }

    private fun processApiResponse(apiData: WeatherApiResponse): ProcessedWeatherData? {
        val mainStats = apiData.mainStats
        val weatherDesc = apiData.weatherInfo?.firstOrNull()

        if (mainStats == null || weatherDesc == null || apiData.cityName == null) {
            Log.w("WeatherViewModelDebug", "processApiResponse: Skipping due to null mainStats, weatherDesc, or cityName.")
            return null
        }


        val tempCelsius = mainStats.temperature?.let { it - 273.15 }?.roundToInt()
        val feelsLikeCelsius = mainStats.feelsLike?.let { it - 273.15 }?.roundToInt()

        return ProcessedWeatherData(
            cityName = apiData.cityName,
            temperature = tempCelsius?.let { "$it°C" } ?: "N/A",
            condition = weatherDesc.main ?: "N/A",
            conditionDescription = weatherDesc.description?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } ?: "N/A",
            humidity = mainStats.humidity?.let { "$it%" } ?: "N/A",
            wind = apiData.wind?.speed?.let { "%.1f m/s".format(Locale.US, it) } ?: "N/A", // Added Locale.US
            feelsLike = feelsLikeCelsius?.let { "$it°C" } ?: "N/A",
            iconCode = weatherDesc.icon
        )
    }

    // New function to process the tide forecast data
    private fun processTideForecast(tides: List<TideInfo>): List<ProcessedTideInfo> {
        Log.d("WeatherViewModelDebug", "processTideForecast: Received ${tides.size} raw tide items.")
        return tides.mapNotNull { tide ->
            Log.d("WeatherViewModelDebug", "Processing raw tide: Time=${tide.time}, Type=${tide.type}, Height=${tide.height}")

            val timeString = tide.time
            val rawType = tide.type // Keep original type for capitalization
            val height = tide.height

            if (timeString == null || rawType == null || height == null) {
                Log.w("WeatherViewModelDebug", "Skipping tide item due to null values: Time=$timeString, Type=$rawType, Height=$height")
                null // Skip invalid entries
            } else {
                try {
                    val zonedDateTime = ZonedDateTime.parse(timeString)
                    val formattedTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).format(tideTimeFormatter)
                    val capitalizedType = rawType.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    val formattedHeight = "%.2fm".format(Locale.US, height) // Added Locale.US

                    Log.d("WeatherViewModelDebug", "Successfully processed to: FormattedTime=$formattedTime, Type=$capitalizedType, Height=$formattedHeight")

                    ProcessedTideInfo(
                        formattedTime = formattedTime,
                        type = capitalizedType, // Use the capitalized type
                        height = formattedHeight
                    )
                } catch (e: DateTimeParseException) {
                    Log.e("WeatherViewModelDebug", "Failed to parse tide time: '$timeString'. Error: ${e.message}", e)
                    null // Skip if time parsing fails
                } catch (e: Exception) {
                    Log.e("WeatherViewModelDebug", "Unexpected error processing tide: Time='$timeString'. Error: ${e.message}", e)
                    null
                }
            }
        }
    }
}