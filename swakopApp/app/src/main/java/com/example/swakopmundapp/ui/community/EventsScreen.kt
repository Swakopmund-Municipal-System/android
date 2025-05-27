package com.example.swakopmundapp.ui.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.navigation.Screen

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val imageResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(navController: NavHostController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community Events") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0277BD),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    if (navController != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
                    .padding(end = 12.dp), // Add padding for scrollbar
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                sampleEvents.forEach { event ->
                    EventCard(
                        event = event,
                        onClick = {
                            navController?.navigate("event_detail/${event.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EventCard(
    event: Event,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = event.imageResId),
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp), // Reduced height to make cards more compact
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp) // Reduced padding
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "📅 ${event.date}   📍${event.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

// Sample data
private val sampleEvents = listOf(
    Event(
        id = "1",
        title = "Swakop Beach Festival",
        description = "Experience the ultimate beach celebration! Enjoy live music, food stalls, water sports, and cultural dances at the ocean.",
        date = "24 June 2025",
        location = "Swakopmund Main Beach",
        imageResId = R.drawable.beach_festival
    ),
    Event(
        id = "2",
        title = "Desert Marathon",
        description = "Annual marathon through the beautiful Namib Desert landscape.",
        date = "15 July 2025",
        location = "Swakopmund Desert",
        imageResId = R.drawable.desert_marathon
    ),
    Event(
        id = "3",
        title = "Cultural Food Festival",
        description = "Taste the diverse flavors of Namibian cuisine.",
        date = "5 August 2025",
        location = "Town Center",
        imageResId = R.drawable.food_festival
    ),
    Event(
        id = "4",
        title = "Amis Day",
        description = "Celebrate the rich cultural heritage of the Amis community with traditional dances, music, and ceremonies.",
        date = "20 August 2025",
        location = "Community Center",
        imageResId = R.drawable.amis_day
    ),
    Event(
        id = "5",
        title = "Trade Fair",
        description = "Annual trade fair showcasing local businesses, crafts, and products from across Namibia.",
        date = "10 September 2025",
        location = "Exhibition Center",
        imageResId = R.drawable.trade_fair
    )
)
