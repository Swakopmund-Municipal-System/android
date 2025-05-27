package com.example.swakopmundapp.ui.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar

@Composable
fun CommunityScreen(navController: NavHostController? = null) {
    CommunityMainMenuScreen(navController = navController)
}

@Composable
fun CommunityMainMenuScreen(navController: NavHostController? = null) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(colorResource(id = R.color.bluebar))
            ) {
                // Back arrow if navigable
                if (navController != null && navController.previousBackStackEntry != null) {
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
                }
                Text(
                    text = "Community",
                    color = Color.White,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = Screen.Community.route,
                navController = navController ?: return@Scaffold
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            MenuItem(
                title = "Emergency Contacts",
                icon = Icons.Filled.Phone,
                onClick = { navController?.navigate(Screen.EmergencyContacts.route) }
            )
            Divider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))
            MenuItem(
                title = "Events",
                icon = Icons.Filled.CalendarToday,
                onClick = { navController?.navigate(Screen.Events.route) }
            )
        }
    }
}


@Composable
private fun MenuItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = title
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    )
}

@Preview(showBackground = true, name = "Community Screen Preview")
@Composable
fun PreviewCommunityScreenWithTheming() {
    CommunityScreen(navController = null)
}

@Preview(showBackground = true, name = "Community Menu Screen Preview")
@Composable
fun PreviewCommunityMainMenuScreenWithTheming() {
    CommunityMainMenuScreen(navController = null)
}
