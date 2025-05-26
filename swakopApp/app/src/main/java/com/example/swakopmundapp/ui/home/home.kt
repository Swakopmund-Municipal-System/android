package com.example.swakopmundapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavItem
import com.example.swakopmundapp.ui.shared.ImageCarousel
import com.example.swakopmundapp.ui.shared.MenuGridItem

data class MenuItem(
    val icon: ImageVector,
    val label: String,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val menuItems = listOf(
        MenuItem(
            icon = Icons.Filled.AccountBalance,
            label = "Municipal",
            route = Screen.Municipal.route
        ),
        MenuItem(
            icon = Icons.Filled.Info,
            label = "About",
            route = "about"
        ),
        MenuItem(
            icon = Icons.Filled.LocationCity,
            label = "Tourism",
            route = "tourism"
        ),
        MenuItem(
            icon = Icons.Filled.People,
            label = "Community",
            route = "community"
        ),
        MenuItem(
            icon = Icons.Filled.Support,
            label = "Support",
            route = "support"
        ),
        MenuItem(
            icon = Icons.Filled.CurrencyExchange,
            label = "Currency\nConverter",
            route = "currency"
        ),
        MenuItem(
            icon = Icons.Filled.WbSunny,
            label = "Weather",
            route = "weather"
        ),
        MenuItem(
            icon = Icons.Filled.GridView,
            label = "Favourite\nMemories",
            route = "memories"
        ),
        MenuItem(
            icon = Icons.Filled.Place,
            label = "Where To\nStay",
            route = "stay"
        )
    )

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavItem(
                    icon = Icons.Filled.Home,
                    label = "Home",
                    isSelected = true,
                    onClick = { }
                )

                BottomNavItem(
                    icon = Icons.Filled.LocationOn,
                    label = "Explore",
                    isSelected = false,
                    onClick = { }
                )

                BottomNavItem(
                    icon = Icons.Filled.Notifications,
                    label = "Notifications",
                    isSelected = false,
                    onClick = { }
                )

                BottomNavItem(
                    icon = Icons.Filled.Person,
                    label = "Profile",
                    isSelected = false,
                    onClick = { }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Image carousel at the top
            ImageCarousel(imageResId = R.drawable.swakopmund_beach)

            // Grid of menu items
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(menuItems) { item ->
                    MenuGridItem(
                        icon = item.icon,
                        label = item.label,
                        onClick = {
                            navController.navigate(item.route)
                        }
                    )
                }
            }
        }
    }
}