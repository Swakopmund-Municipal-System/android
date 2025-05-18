package com.example.swakopmundapp.ui.navigation

import TourismGridScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swakopmundapp.data.model.tourism.TourismViewModel
import com.example.swakopmundapp.ui.about.AboutScreen
import com.example.swakopmundapp.ui.community.CommunityScreen
import com.example.swakopmundapp.ui.currency.CurrencyConverterScreen
import com.example.swakopmundapp.ui.currency.ExchangeChartScreen
import com.example.swakopmundapp.ui.favorites.FavouriteMemoriesScreen
import com.example.swakopmundapp.ui.home.HomeScreen
import com.example.swakopmundapp.ui.login.LoginScreen
import com.example.swakopmundapp.ui.map.MapScreen
import com.example.swakopmundapp.ui.notifications.NotificationScreen
import com.example.swakopmundapp.ui.profile.EditProfileScreen
import com.example.swakopmundapp.ui.profile.ForgotPasswordScreen
import com.example.swakopmundapp.ui.profile.ProfileScreen
import com.example.swakopmundapp.ui.resident.MunicipalScreen
import com.example.swakopmundapp.ui.startscreen.StartScreen
import com.example.swakopmundapp.ui.support.SupportScreen
import com.example.swakopmundapp.ui.tourism.TourismDetailScreen
import com.example.swakopmundapp.ui.weather.WeatherScreen
import com.example.swakopmundapp.ui.wheretostay.WhereToStayScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavGraph(navController: NavHostController) { NavHost(navController = navController, startDestination = Screen.Start.route)
    {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Municipal.route) { MunicipalScreen() }
        composable(Screen.About.route) { AboutScreen()
        }
        composable(Screen.TourismGrid.route) {
            val viewModel = TourismViewModel()
            TourismGridScreen(navController, viewModel)
        }

        composable(
            Screen.TourismDetail.route,
            arguments = listOf(navArgument("activityName") { type = NavType.StringType })
        ) { backStackEntry ->
            val activityName = backStackEntry.arguments?.getString("activityName") ?: ""
            val viewModel = TourismViewModel()
            TourismDetailScreen(navController, activityName, viewModel)
        }


        composable(Screen.Community.route) { CommunityScreen() }
        composable(Screen.Support.route) { SupportScreen() }
        composable(Screen.CurrencyConverter.route) { CurrencyConverterScreen(navController) }
        composable(Screen.Weather.route) { WeatherScreen() }
        composable(Screen.FavouriteMemories.route) { FavouriteMemoriesScreen() }
        composable(Screen.WhereToStay.route) { WhereToStayScreen() }
        composable(Screen.Map.route) { MapScreen(navController) }
        composable(Screen.Notifications.route) { NotificationScreen(navController) }

//        Profile navigations --------------------------------------
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





    }
}
