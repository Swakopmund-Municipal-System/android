package com.example.swakopmundapp.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.ui.navigation.Screen

@Composable
fun BottomNavBar(
    currentRoute: String,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(
            icon = Icons.Filled.Home,
            label = "Home",
            isSelected = currentRoute == Screen.Home.route,
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        // Pop up to the start destination to avoid building up a stack
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            }
        )

        BottomNavItem(
            icon = Icons.Filled.LocationOn,
            label = "Explore",
            isSelected = currentRoute == Screen.Map.route,
            onClick = {
                if (currentRoute != Screen.Map.route) {
                    navController.navigate(Screen.Map.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        BottomNavItem(
            icon = Icons.Filled.Notifications,
            label = "Notifications",
            isSelected = currentRoute == Screen.Map.route,
            onClick = {
                if (currentRoute != Screen.Map.route) {
                    navController.navigate(Screen.Map.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        BottomNavItem(
            icon = Icons.Filled.Person,
            label = "Profile",
            isSelected = currentRoute == Screen.Map.route,
            onClick = {
                if (currentRoute != Screen.Map.route) {
                    navController.navigate(Screen.Map.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}