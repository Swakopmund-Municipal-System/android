package com.example.swakopmundapp.ui.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(navController: NavHostController? = null, eventId: String? = null) {
    // Get the event based on the ID
    val event = when (eventId) {
        "1" -> Event(
            id = "1",
            title = "Swakop Beach Festival",
            description = "Experience the ultimate beach celebration! Enjoy live music, food stalls, water sports, and cultural dances under the Atlantic sunset. This annual event brings together locals and tourists for a day of fun and entertainment. Don't miss out on the spectacular fireworks display at sunset!",
            date = "24 June 2025",
            location = "Swakopmund Main Beach",
            imageResId = R.drawable.beach_festival
        )
        "2" -> Event(
            id = "2",
            title = "Desert Marathon",
            description = "Annual marathon through the beautiful Namib Desert landscape. Challenge yourself in this unique race that combines athleticism with breathtaking scenery. The marathon features different categories for all fitness levels, from professional runners to casual participants. Experience the magic of running through one of the world's oldest deserts!",
            date = "15 July 2025",
            location = "Swakopmund Desert",
            imageResId = R.drawable.desert_marathon
        )
        "3" -> Event(
            id = "3",
            title = "Cultural Food Festival",
            description = "Taste the diverse flavors of Namibian cuisine. This festival celebrates the rich culinary heritage of Namibia, featuring traditional dishes, modern fusion cuisine, and cooking demonstrations by renowned chefs. Sample local delicacies, learn about traditional cooking methods, and enjoy live entertainment while savoring the best of Namibian gastronomy.",
            date = "5 August 2025",
            location = "Town Center",
            imageResId = R.drawable.food_festival
        )
        "4" -> Event(
            id = "4",
            title = "Amis Day",
            description = "Celebrate the Amis day club with live djs, dances, music, great food and good vibes. This special day happens only 3 times a year , featuring authentic performances. Join us in preserving and celebrating this important part of our cultural heritage.",
            date = "20 August 2025",
            location = "Community Center",
            imageResId = R.drawable.amis_day
        )
        "5" -> Event(
            id = "5",
            title = "Trade Fair",
            description = "Annual trade fair showcasing local businesses, crafts, and products from across Namibia. This premier business event brings together entrepreneurs, artisans, and companies from various sectors. Network with industry leaders, discover innovative products, and explore business opportunities in a vibrant marketplace setting.",
            date = "10 September 2025",
            location = "Exhibition Center",
            imageResId = R.drawable.trade_fair
        )
        else -> Event(
            id = "1",
            title = "Swakop Beach Festival",
            description = "Experience the ultimate beach celebration! Enjoy live music, food stalls, water sports, and cultural dances under the Atlantic sunset. This annual event brings together locals and tourists for a day of fun and entertainment. Don't miss out on the spectacular fireworks display at sunset!",
            date = "24 June 2025",
            location = "Swakopmund Main Beach",
            imageResId = R.drawable.beach_festival
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Event Image
            Image(
                painter = painterResource(id = event.imageResId),
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Event Details
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Date",
                        tint = Color(0xFF0277BD)
                    )
                    Text(
                        text = event.date,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Location
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color(0xFF0277BD)
                    )
                    Text(
                        text = event.location,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Description
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
