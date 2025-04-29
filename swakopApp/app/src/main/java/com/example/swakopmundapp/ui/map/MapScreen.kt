package com.example.swakopmundapp.ui.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar

@Composable
fun MapScreen(navController: NavHostController) {
    // State for the selected adventure type
    var selectedType by remember { mutableStateOf("Foodie") }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = Screen.Map.route,
                navController = navController
            )
        }
    ) { paddingValues ->
        // This Box contains the map and overlay elements
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Map image from the URL you provided
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://media.wired.com/photos/59269cd37034dc5f91bec0f1/191:100/w_1280,c_limit/GoogleMapTA.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "Map of Swakopmund",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Adventure card at the bottom
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Start Adventure",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0A4D85)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Location section
                    Text(
                        text = "Your Location",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Demekaar and Delulu street",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Adventure type section
                    Text(
                        text = "Type",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Adventure type options in a grid layout
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Foodie option
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clickable { selectedType = "Foodie" },
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedType == "Foodie")
                                    Color(0xFFF5F5F5) else Color(0xFFF5F5F5)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Foodie",
                                    fontSize = 16.sp,
                                    fontWeight = if (selectedType == "Foodie")
                                        FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }

                        // Adventure option
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clickable { selectedType = "Adventure" },
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedType == "Adventure")
                                    Color(0xFFF5F5F5) else Color(0xFFF5F5F5)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.swakopmund_beach), // Replace with correct icon
                                    contentDescription = "Adventure",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(
                                    text = "Adventure",
                                    fontSize = 16.sp,
                                    fontWeight = if (selectedType == "Adventure")
                                        FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Historian option in second row
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(48.dp)
                            .clickable { selectedType = "Historian" },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedType == "Historian")
                                Color(0xFFF5F5F5) else Color(0xFFF5F5F5)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = "Historian",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "Historian",
                                fontSize = 16.sp,
                                fontWeight = if (selectedType == "Historian")
                                    FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Search button
                    Button(
                        onClick = { /* Handle search */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0A4D85)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Search",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}