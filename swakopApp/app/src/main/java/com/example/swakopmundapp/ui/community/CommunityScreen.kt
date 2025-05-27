package com.example.swakopmundapp.ui.community

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.swakopmundapp.ui.navigation.Screen

@Composable
fun CommunityScreen(navController: NavHostController? = null) {
    CommunityMainMenuScreen(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityMainMenuScreen(navController: NavHostController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0277BD),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    if (navController != null && navController.previousBackStackEntry != null) {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
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

            Divider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))
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
