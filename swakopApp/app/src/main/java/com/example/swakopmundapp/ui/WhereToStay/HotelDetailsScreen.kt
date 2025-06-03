package com.example.swakopmundapp.ui.wheretostay

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.ui.WhereToStay.Hotel
import com.example.swakopmundapp.ui.wheretostay.components.CalendarPicker
import java.time.LocalDate

@Composable
fun HotelDetailsScreen(hotel: Hotel, navController: NavHostController) {
    var checkIn by remember { mutableStateOf<LocalDate?>(null) }
    var checkOut by remember { mutableStateOf<LocalDate?>(null) }

    Column(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = hotel.imageRes),
            contentDescription = hotel.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Column(Modifier.padding(16.dp)) {
            Text(hotel.name, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text("Enjoy a private room at Beach Hotel with scenic views of Swakopmund’s coastline...")

            Spacer(Modifier.height(16.dp))
            Text("What this place offers", style = MaterialTheme.typography.titleMedium)

            val amenities = listOf("Wifi", "City skyline view", "Workspace", "Security", "Lock on door")
            amenities.take(4).forEach {
                Text("• $it", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(12.dp))
            Button(onClick = { /* show all */ }) {
                Text("Show all 28 amenities")
            }

            Spacer(Modifier.height(24.dp))

            Text("Check availability", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            CalendarPicker("Check-in date", checkIn) { checkIn = it }
            Spacer(Modifier.height(8.dp))
            CalendarPicker("Check-out date", checkOut) { checkOut = it }

            Spacer(Modifier.height(16.dp))

            Button(onClick = { /* proceed to booking */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Booking Now - ${hotel.price}/night")
            }
        }
    }
}
