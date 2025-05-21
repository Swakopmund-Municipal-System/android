package com.example.swakopmundapp.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar
import com.example.swakopmundapp.ui.shared.TopBlueBar

@Composable
fun ProfileScreen(navController: NavHostController) {
    val userName = "User Name"
    val userEmail = "user@gmail.com"
    val profileImage = "https://i.pinimg.com/736x/3f/94/70/3f9470b34a8e3f526dbdb022f9f19cf7.jpg" // Placeholder image

    Scaffold(
        topBar = {
            TopBlueBar(
                title = "Profile Screen",
                navController = navController
            )
        },
        bottomBar = {
            BottomNavBar(currentRoute = Screen.Profile.route, navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = rememberAsyncImagePainter(profileImage),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = userEmail, color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            ProfileItem("Edit Profile", Icons.Default.Edit) {
                navController.navigate(Screen.EditProfile.route)
            }

            ProfileItem("Add Pin", Icons.Default.Place) {
                // navController.navigate(Screen.AddPin.route) // Optional
            }

            ProfileItem("Invite a Friend", Icons.Default.Share) {
                // Share intent logic
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileItem("Logout", Icons.AutoMirrored.Filled.ExitToApp, Color.Red) {
                // handle logout
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, icon: ImageVector, iconTint: Color = Color.Black, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconTint)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, fontSize = 16.sp)
    }
}
