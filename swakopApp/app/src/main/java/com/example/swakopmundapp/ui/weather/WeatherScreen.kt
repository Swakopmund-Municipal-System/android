package com.example.swakopmundapp.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherScreen(navController: NavHostController) {
    val isDayTime = remember { LocalDateTime.now().hour in 6..18 }

    // Background based on time
    val backgroundImage = painterResource(
        if (isDayTime) R.drawable.weather else R.drawable.weather
    )

    val weather = WeatherData(
        temperature = if (isDayTime) 40 else 25,
        condition = if (isDayTime) "Clear" else "Clouds",
        humidity = "72%",
        wind = "5.60km/h",
        feelsLike = "24°C",
        forecast = listOf(
            Forecast("Wed 16", "25°", R.drawable.partly_cloudy),
            Forecast("Thu 17", "22°", R.drawable.sunny),
            Forecast("Fri 18", "24°", R.drawable.partly_cloudy),
            Forecast("Sat 19", "26°", R.drawable.partly_cloudy)
        )
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(colorResource(id = R.color.bluebar))
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Weather",
                    color = Color.White,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            // Overlay for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f))
                        )
                    )
            )

            // Weather Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Swakopmund",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.bluebar)
                    )
                    Text(
                        "Updated ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))}",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.bluebar)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Main Weather Info
                    Text(
                        text = weather.condition,
                        fontSize = 24.sp,
                        color = colorResource(id = R.color.bluebar)
                    )
                    Text(
                        text = "${weather.temperature}°C",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.bluebar)
                    )

                    Text(
                        text = if (isDayTime) "Stay Hydrated for the Day" else "Time to Wind Down",
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.bluebar)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherDetailItem("HUMIDITY", weather.humidity)
                        WeatherDetailItem("WIND", weather.wind)
                        WeatherDetailItem("FEELS LIKE", weather.feelsLike)
                    }
                }

                // Forecast
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(weather.forecast) { item ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(item.day, color = colorResource(id = R.color.bluebar))
                            Image(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = null
                            )
                            Text(item.temp, color = colorResource(id = R.color.bluebar))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 12.sp, color = colorResource(id = R.color.bluebar))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = colorResource(id = R.color.bluebar))
    }
}

data class Forecast(val day: String, val temp: String, val iconRes: Int)
data class WeatherData(
    val temperature: Int,
    val condition: String,
    val humidity: String,
    val wind: String,
    val feelsLike: String,
    val forecast: List<Forecast>
)
