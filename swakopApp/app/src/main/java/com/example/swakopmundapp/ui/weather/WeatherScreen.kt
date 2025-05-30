//@file:OptIn(ExperimentalMaterial3Api::class) // Add if specific M3 features require it

package com.example.swakopmundapp.ui.weather

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush // Removed for no background image
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale // Removed for no background image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource // Keep if R.color.bluebar is essential
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swakopmundapp.R // Keep for R.drawable references if they remain
import org.koin.androidx.compose.koinViewModel

// Assuming your data classes (ProcessedWeatherData, ProcessedTideInfo) and ViewModel are defined

@OptIn(ExperimentalMaterial3Api::class) // For CenterAlignedTopAppBar
@Composable
fun WeatherScreen(
    onBack: () -> Unit = {},
    viewModel: WeatherViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface, // Use a theme surface color
        topBar = {
            WeatherTopBar(
                title = "Weather & Tides", // Keeping hardcoded for now
                onBack = onBack
            )
        }
    ) { paddingValues ->
        // Main content area
        Box( // Using Box to handle overall loading/error states centrally
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                // Initial full loading state
                uiState.isLoading && uiState.weatherData == null && uiState.tideForecast == null -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                // Initial full error state
                uiState.error != null && uiState.weatherData == null && uiState.tideForecast == null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error Loading Data", // Generic error title
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = uiState.error ?: "An unknown error occurred.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.fetchAllWeatherData() },
                            modifier = Modifier.padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Retry", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
                // Content available or partial loading/error
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()) // Allow scrolling for content
                            .padding(horizontal = 16.dp, vertical = 20.dp), // Increased vertical padding
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(28.dp) // Increased spacing
                    ) {
                        // Current Weather Section
                        uiState.weatherData?.let { weather ->
                            CurrentWeatherSection(
                                weather = weather,
                                lastUpdated = uiState.lastUpdated,
                                isDayTime = uiState.isDayTime // Retaining isDayTime logic for now, though it's not used visually in this version
                            )
                        } ?: run {
                            // Handle cases where only weather data is missing/loading/error
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(vertical = 20.dp).size(40.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                )
                            } else if (uiState.error?.contains("Current Weather API Error", ignoreCase = true) == true) {
                                Text(
                                    "Failed to load current weather: ${uiState.error!!.substringAfter("Error: ", "Details unavailable.")}",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                            } else {
                                // Placeholder if weather data is null for other reasons (e.g. after initial load)
                                Box(Modifier.height(20.dp))
                            }
                        }

                        // Tide Forecast Section
                        val tideErrorMessage = when {
                            uiState.error?.contains("Tide Forecast API Error", ignoreCase = true) == true ->
                                "Failed to load tide data: ${uiState.error!!.substringAfter("Error: ", "Details unavailable.")}"
                            uiState.tideForecast == null && uiState.weatherData != null && uiState.error == null && !uiState.isLoading ->
                                "Tide data not available at the moment."
                            else -> null
                        }
                        TideForecastSection(
                            tideForecast = uiState.tideForecast,
                            isLoading = uiState.isLoading && uiState.tideForecast == null, // Show loading only if tide data is null AND overall loading
                            errorMessage = tideErrorMessage
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(title: String, onBack: () -> Unit) {
    // Using CenterAlignedTopAppBar for a more standard Material 3 look
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                // Using onPrimaryContainer for better contrast with primaryContainer
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            // A common M3 pattern for top app bars
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}


@Composable
fun CurrentWeatherSection(weather: ProcessedWeatherData, lastUpdated: String?, isDayTime: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Consistent spacing
    ) {
        Text(
            weather.cityName,
            style = MaterialTheme.typography.headlineSmall, // Adjusted for typical screen size
            color = MaterialTheme.colorScheme.onSurface
        )
//        lastUpdated?.let {
//            Text(
//                "Updated $it",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant // Subtler color
//            )
//        }

        Spacer(modifier = Modifier.height(16.dp))

        weather.iconCode?.let { icon ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://openweathermap.org/img/wn/${icon}@4x.png")
                    .crossfade(true)
                    .build(),
                // Using existing drawables as placeholders
                placeholder = painterResource(R.drawable.partly_cloudy),
                error = painterResource(R.drawable.partly_cloudy),
                contentDescription = weather.conditionDescription,
                modifier = Modifier.size(100.dp)
            )
        }
        Text(
            weather.conditionDescription,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            weather.temperature,
            // Larger, more prominent temperature
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary // Highlight temperature
        )

        // The "Enjoy the day" text can be conditional if you have other uses for isDayTime
        // For now, removing if it's purely for background image logic previously.

        // Text(
        // text = if (isDayTime) "Enjoy the day!" else "Good evening!",
        // style = MaterialTheme.typography.bodyMedium,
        // color = MaterialTheme.colorScheme.onSurfaceVariant,
        // modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        // )
        Spacer(modifier = Modifier.height(16.dp)) // Added space if the above text is removed


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherDetailItem("HUMIDITY", weather.humidity)
            WeatherDetailItem("WIND", weather.wind)
            WeatherDetailItem("FEELS LIKE", weather.feelsLike)
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            label.uppercase(), // Make label distinct
            style = MaterialTheme.typography.labelSmall, // Appropriate for labels
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun TideForecastSection(
    tideForecast: List<ProcessedTideInfo>?,
    isLoading: Boolean,
    errorMessage: String?
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(
            "Tide Forecast",
            style = MaterialTheme.typography.titleLarge, // More prominent section title
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp).align(Alignment.Start)
        )
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }
            errorMessage != null -> {
                Text(
                    errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(horizontal = 8.dp)
                )
            }
            !tideForecast.isNullOrEmpty() -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp) // Give cards some breathing room
                ) {
                    items(items = tideForecast, key = { tide -> "${tide.type}-${tide.formattedTime}" }) { tideItem ->
                        TideCard(tideItem)
                    }
                }
            }
            else -> {
                Text(
                    "No tide data available for display.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun TideCard(tide: ProcessedTideInfo) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f), // Subtle card bg
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant // Default content color for this card
        ),
        modifier = Modifier.width(115.dp) // Adjusted width slightly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .fillMaxWidth() // Column takes the width of the Card
        ) {
            val tideTypeColor = if (tide.type.equals("High", ignoreCase = true)) {
                MaterialTheme.colorScheme.primary // Use primary for High tide
            } else {
                MaterialTheme.colorScheme.secondary // Use secondary for Low tide (or another distinct color)
            }

            Text(
                text = tide.type,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = tideTypeColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = tide.formattedTime,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface // Main text color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tide.height,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Subtler for detail
            )
        }
    }
}