package com.example.swakopmundapp.ui.wheretostay

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.swakopmundapp.R
import java.time.LocalDate

@Composable
fun WhereToStayScreen() {
    var showCalendar by remember { mutableStateOf(false) }
    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

    val hotels = listOf(
        Hotel("Beach Hotel Swakopmund", "NAD 580", "25% OFF", 3.9, 200, R.drawable.beach_hotel),
        Hotel("Moringa Gardens", "NAD 1200", null, 4.3, 150, R.drawable.moringa),
        Hotel("Loewenhoff Suite", "NAD 100", "15% OFF", 4.5, 20, R.drawable.loewenhoff),
        Hotel("Giardino Boutique", "NAD 127", "25% OFF", 4.0, 20, R.drawable.giardino)
    )

    if (showCalendar) {
        CalendarDialog(
            onDismiss = { showCalendar = false },
            onDatesSelected = { checkIn, checkOut ->
                checkInDate = checkIn
                checkOutDate = checkOut
                Log.d("Booking", "Check-in: $checkInDate, Check-out: $checkOutDate")
                showCalendar = false
            }
        )
    }

    LazyColumn(Modifier.padding(16.dp)) {
        item {
            Text("Currently Trending", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(hotels) { hotel ->
            HotelCard(hotel = hotel) {
                showCalendar = true
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun HotelCard(hotel: Hotel, onBookClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = hotel.imageRes),
                contentDescription = hotel.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
            Text(hotel.name, style = MaterialTheme.typography.titleMedium)
            Text("⭐ ${hotel.rating} (${hotel.reviews} reviews)")
            hotel.discount?.let {
                Text(it, color = MaterialTheme.colorScheme.primary)
            }
            Text(hotel.price)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBookClick) {
                Text("Book now")
            }
        }
    }
}

@Composable
fun CalendarDialog(
    onDismiss: () -> Unit,
    onDatesSelected: (LocalDate, LocalDate) -> Unit
) {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    val today = LocalDate.now()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (startDate != null && endDate != null) {
                Button(onClick = { onDatesSelected(startDate!!, endDate!!) }) {
                    Text("Confirm")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Select Dates") },
        text = {
            Column {
                Text("Pick check-in and check-out dates (simplified)")
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { startDate = today }) {
                        Text("Check-in: ${startDate ?: "Not Set"}")
                    }
                    Button(onClick = { endDate = today.plusDays(3) }) {
                        Text("Check-out: ${endDate ?: "Not Set"}")
                    }
                }
            }
        }
    )
}

data class Hotel(
    val name: String,
    val price: String,
    val discount: String?,
    val rating: Double,
    val reviews: Int,
    val imageRes: Int
)
