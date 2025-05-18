package com.example.swakopmundapp.ui.wheretostay

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.ui.navigation.Screen
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun WhereToStayScreen(navController: NavHostController, hotels: List<Hotel>) {
    var showCalendar by remember { mutableStateOf(false) }
    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

    var searchQuery by remember { mutableStateOf("") }
    var selectedSort by remember { mutableStateOf("None") }

    val sortOptions = listOf(
        "None",
        "Price (Low → High)",
        "Price (High → Low)",
        "Rating (High → Low)",
        "Name (A → Z)"
    )

    val filteredHotels = hotels
        .filter { it.name.contains(searchQuery, ignoreCase = true) }
        .let {
            when (selectedSort) {
                "Price (Low → High)" -> it.sortedBy { hotel -> hotel.priceNumeric }
                "Price (High → Low)" -> it.sortedByDescending { hotel -> hotel.priceNumeric }
                "Rating (High → Low)" -> it.sortedByDescending { hotel -> hotel.rating }
                "Name (A → Z)" -> it.sortedBy { hotel -> hotel.name }
                else -> it
            }
        }

    if (showCalendar) {
        CalendarBottomSheet(
            onDismiss = { showCalendar = false },
            onConfirm = { checkIn, checkOut ->
                checkInDate = checkIn
                checkOutDate = checkOut
                Log.d("Booking", "Check-in: $checkInDate, Check-out: $checkOutDate")
                val nights = calculateNights(checkIn, checkOut)
                Log.d("Booking", "Nights: $nights")
                showCalendar = false
            }
        )
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Amenities ▼") // Placeholder for future functionality

            SortDropdown(
                selected = selectedSort,
                options = sortOptions,
                onOptionSelected = { selectedSort = it }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Currently Trending", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(filteredHotels) { hotel ->
                HotelCard(hotel = hotel, navController = navController)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun HotelCard(
    hotel: Hotel,
    navController: NavHostController
) {
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
                Text(it, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Text(hotel.price, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.HotelDetails.withArgs(hotel.name))
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Book now")
            }
        }
    }
}

@Composable
fun SortDropdown(
    selected: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            text = "Sort by ▼",
            modifier = Modifier.clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate, LocalDate) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    var checkInDate by remember { mutableStateOf<LocalDate?>(null) }
    var checkOutDate by remember { mutableStateOf<LocalDate?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.85f)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("When’s your next trip?", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            TabRow(selectedTabIndex = tabIndex) {
                Tab(selected = tabIndex == 0, onClick = { tabIndex = 0 }) {
                    Text("Check-In")
                }
                Tab(selected = tabIndex == 1, onClick = { tabIndex = 1 }) {
                    Text("Check-Out")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (tabIndex == 0) {
                DatePickerCalendar(
                    selectedDate = checkInDate,
                    onDateSelected = { checkInDate = it }
                )
            } else {
                DatePickerCalendar(
                    selectedDate = checkOutDate,
                    onDateSelected = { checkOutDate = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
                Button(
                    enabled = checkInDate != null && checkOutDate != null,
                    onClick = {
                        onConfirm(checkInDate!!, checkOutDate!!)
                    }
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}

@Composable
fun DatePickerCalendar(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = remember { LocalDate.now() }
    val currentMonth = remember { mutableStateOf(today.withDayOfMonth(1)) }

    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {
                currentMonth.value = currentMonth.value.minusMonths(1)
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
            }

            Text(
                text = currentMonth.value.month.name.lowercase()
                    .replaceFirstChar { it.uppercase() } + " ${currentMonth.value.year}",
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(onClick = {
                currentMonth.value = currentMonth.value.plusMonths(1)
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach {
                Text(it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        val days = buildCalendar(currentMonth.value)
        days.chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                week.forEach { date ->
                    val isSelected = date == selectedDate
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable { onDateSelected(date) },
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                            tonalElevation = if (isSelected) 4.dp else 0.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = if (isSelected)
                                            MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.onBackground,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun buildCalendar(firstDayOfMonth: LocalDate): List<LocalDate> {
    val start = firstDayOfMonth.withDayOfMonth(1)
        .with(DayOfWeek.SUNDAY)
        .minusDays(firstDayOfMonth.withDayOfMonth(1).dayOfWeek.value % 7L)
    return List(42) { start.plusDays(it.toLong()) }
}

private fun calculateNights(checkIn: LocalDate, checkOut: LocalDate): Long {
    return ChronoUnit.DAYS.between(checkIn, checkOut)
}

data class Hotel(
    val name: String,
    val price: String,
    val discount: String?,
    val rating: Double,
    val reviews: Int,
    val imageRes: Int
) {
    val priceNumeric: Int
        get() = price.filter { it.isDigit() }.toIntOrNull() ?: 0
}
