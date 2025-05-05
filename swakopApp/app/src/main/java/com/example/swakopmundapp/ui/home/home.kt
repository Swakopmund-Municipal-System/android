package com.example.swakopmundapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.People
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
import com.example.swakopmundapp.ui.shared.BottomNavBar
import com.example.swakopmundapp.ui.shared.ImageCarousel
import com.example.swakopmundapp.ui.shared.MenuGridItem
import com.example.swakopmundapp.ui.shared.TopBlueBar

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
            route = Screen.About.route
        ),
        MenuItem(
            icon = Icons.Filled.LocationCity,
            label = "Tourism",
            route = Screen.Tourism.route
        ),
        MenuItem(
            icon = Icons.Filled.People,
            label = "Community",
            route = Screen.Community.route
        ),
        MenuItem(
            icon = Icons.Filled.Support,
            label = "Support",
            route = Screen.Support.route
        ),
        MenuItem(
            icon = Icons.Filled.CurrencyExchange,
            label = "Currency\nConverter",
            route = Screen.CurrencyConverter.route
        ),
        MenuItem(
            icon = Icons.Filled.WbSunny,
            label = "Weather",
            route = Screen.Weather.route
        ),
        MenuItem(
            icon = Icons.Filled.GridView,
            label = "Favourite\nMemories",
            route = Screen.FavouriteMemories.route
        ),
        MenuItem(
            icon = Icons.Filled.Place,
            label = "Where To\nStay",
            route = Screen.WhereToStay.route
        )
    )

    Scaffold(
        topBar = { TopBlueBar(title = "Home") },
        bottomBar = {
            BottomNavBar(
                currentRoute = Screen.Home.route,
                navController = navController
            )
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