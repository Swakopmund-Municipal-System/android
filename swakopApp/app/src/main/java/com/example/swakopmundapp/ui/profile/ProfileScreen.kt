package com.example.swakopmundapp.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar
import com.example.swakopmundapp.ui.shared.TopBlueBar
import com.example.swakopmundapp.viewModel.UserViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.swakopmundapp.repository.transaction.TransactionHandler
import androidx.compose.runtime.livedata.observeAsState
import com.example.swakopmundapp.ui.components.LogoutItem
import com.example.swakopmundapp.ui.components.ProfileItem

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: UserViewModel = koinViewModel()
) {

    val userName = "User Name"
    val userEmail = "user@gmail.com"
    val profileImage = "https://i.pinimg.com/736x/3f/94/70/3f9470b34a8e3f526dbdb022f9f19cf7.jpg" // Placeholder image

    var isLoggingOut by remember { mutableStateOf(false) }
    var logoutResult by remember { mutableStateOf<LiveData<TransactionHandler<out Any?>>?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // Observe logout result
    logoutResult?.observeAsState()?.value?.let { result ->

        when (result) {

            is TransactionHandler.Started -> {
                isLoggingOut = true
            }

            is TransactionHandler.SuccessfullyCompleted -> {
                isLoggingOut = false
                viewModel.clearServiceInterceptor()

                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }

                logoutResult = null
            }

            is TransactionHandler.PoorConnection -> {
                isLoggingOut = false
                errorMessage = "Slow internet connection"
            }

            is TransactionHandler.Error -> {
                isLoggingOut = false
                errorMessage = result.error?.localizedMessage ?: "Something went wrong"
            }

            else -> {
                isLoggingOut = false
                errorMessage = (result.data ?: "Something went wrong") as? String ?: "Error"
            }
        }
    }

    Scaffold(
        topBar = { TopBlueBar(title = "User Profile") },
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

            LogoutItem(
                label = "Logout",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                iconTint = Color.Red,
                enabled = !isLoggingOut
            ) {
                if (!isLoggingOut) {
                    logoutResult = viewModel.logout()
                }
            }
        }

        // Loading indicator during logout
        if (isLoggingOut) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
    }
}
