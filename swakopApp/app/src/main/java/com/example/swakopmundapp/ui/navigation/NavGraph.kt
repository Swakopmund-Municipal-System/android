package com.example.swakopmundapp.ui.navigation

import FavouriteMemoriesScreen
import TourismGridScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swakopmundapp.R
import com.example.swakopmundapp.data.model.FavouriteMemories.FavouriteMemoriesViewModel
import com.example.swakopmundapp.data.model.municipal.BankDetailsViewModel
import com.example.swakopmundapp.data.model.tourism.TourismViewModel
import com.example.swakopmundapp.ui.about.AboutScreen
import com.example.swakopmundapp.ui.community.CommunityScreen
import com.example.swakopmundapp.ui.currency.CurrencyConverterScreen
import com.example.swakopmundapp.ui.currency.ExchangeChartScreen
import com.example.swakopmundapp.ui.home.HomeScreen
import com.example.swakopmundapp.ui.login.LoginScreen
import com.example.swakopmundapp.ui.map.MapScreen
import com.example.swakopmundapp.ui.municipal.BankDetailsScreen
import com.example.swakopmundapp.ui.municipal.MunicipalOption
import com.example.swakopmundapp.ui.notifications.NotificationScreen
import com.example.swakopmundapp.ui.profile.EditProfileScreen
import com.example.swakopmundapp.ui.profile.ForgotPasswordScreen
import com.example.swakopmundapp.ui.profile.ProfileScreen
import com.example.swakopmundapp.ui.municipal.MunicipalScreen
import com.example.swakopmundapp.ui.startscreen.StartScreen
import com.example.swakopmundapp.ui.support.SupportScreen
import com.example.swakopmundapp.ui.tourism.TourismDetailScreen
import com.example.swakopmundapp.ui.weather.WeatherScreen
import com.example.swakopmundapp.ui.wheretostay.Hotel
import com.example.swakopmundapp.ui.wheretostay.HotelDetailsScreen
import com.example.swakopmundapp.ui.wheretostay.WhereToStayScreen
import com.example.swakopmundapp.ui.community.EmergencyContactsScreen
import com.example.swakopmundapp.ui.community.EventsScreen
import com.example.swakopmundapp.ui.community.EventDetailScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavGraph(navController: NavHostController) {
    // Define hotel data that can be used across the app
    val hotels = listOf(
        Hotel("Beach Hotel Swakopmund", "NAD 580", "25% OFF", 3.9, 200, R.drawable.beach_hotel),
        Hotel("Moringa Gardens", "NAD 1200", null, 4.3, 150, R.drawable.moringa),
        Hotel("Loewenhoff Suite", "NAD 100", "15% OFF", 4.5, 20, R.drawable.loewenhoff),
        Hotel("Giardino Boutique", "NAD 127", "25% OFF", 4.0, 20, R.drawable.giardino)
    )

    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Municipal.route) {
            MunicipalScreen(
                onBack = { navController.popBackStack() },
                onOptionSelected = { option: MunicipalOption ->
                    when (option) {
                        is MunicipalOption.BankDetails -> {
                            navController.navigate(Screen.BankDetails.route)
                        }
                        // Add more when ready
                        else -> {}
                    }
                }
            )
        }

        composable(Screen.About.route) { AboutScreen() }

        composable(Screen.TourismGrid.route) {
            val viewModel = TourismViewModel()
            TourismGridScreen(navController, viewModel)
        }



        composable(Screen.BankDetails.route) {
            val viewModel = remember { BankDetailsViewModel() }
            BankDetailsScreen(viewModel)
        }

        composable(
            Screen.TourismDetail.route,
            arguments = listOf(navArgument("activityName") { type = NavType.StringType })
        ) { backStackEntry ->
            val activityName = backStackEntry.arguments?.getString("activityName") ?: ""
            val viewModel = TourismViewModel()
            TourismDetailScreen(navController, activityName, viewModel)
        }

        
        composable(Screen.Support.route) { SupportScreen() }
        composable(Screen.CurrencyConverter.route) { CurrencyConverterScreen(navController) }
        composable(Screen.Weather.route) { WeatherScreen() }

        composable(Screen.FavouriteMemories.route) {
            val viewModel = remember { FavouriteMemoriesViewModel() }
            FavouriteMemoriesScreen(viewModel)
        }

        composable(Screen.WhereToStay.route) {
            WhereToStayScreen(navController, hotels)
        }

        composable(Screen.Map.route) { MapScreen(navController) }
        composable(Screen.Notifications.route) { NotificationScreen(navController) }

        //Maria
        
        composable(Screen.Community.route) {
            CommunityScreen(navController)
        }
        composable(Screen.EmergencyContacts.route) {
            EmergencyContactsScreen(navController)
        }
        composable(Screen.Events.route) {
            EventsScreen(navController)
        }
        composable(Screen.EventDetail.route) {
            EventDetailScreen(navController) }
    
        

        // Profile navigations --------------------------------------
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.EditProfile.route) { EditProfileScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.ExchangeChart.route) {
            ExchangeChartScreen()
        }

        composable(Screen.Start.route) {
            StartScreen(navController)
        }

        composable(
            route = "hotel_details/{hotelName}",
            arguments = listOf(navArgument("hotelName") { type = NavType.StringType })
        ) { backStackEntry ->
            val hotelName = backStackEntry.arguments?.getString("hotelName") ?: ""
            val hotel = hotels.find { it.name == hotelName }
            if (hotel != null) {
                HotelDetailsScreen(hotel = hotel, navController = navController)
            }
        }
    }
}
