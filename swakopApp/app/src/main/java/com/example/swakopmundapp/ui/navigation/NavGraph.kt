package com.example.swakopmundapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swakopmundapp.ui.about.AboutScreen
import com.example.swakopmundapp.ui.community.CommunityScreen
import com.example.swakopmundapp.ui.home.HomeScreen
import com.example.swakopmundapp.ui.resident.MunicipalScreen
import com.example.swakopmundapp.ui.converter.CurrencyConverterScreen
import com.example.swakopmundapp.ui.favorites.FavouriteMemoriesScreen
import com.example.swakopmundapp.ui.support.SupportScreen
import com.example.swakopmundapp.ui.tourism.TourismScreen
import com.example.swakopmundapp.ui.weather.WeatherScreen
import com.example.swakopmundapp.ui.wheretostay.WhereToStayScreen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Municipal.route) { MunicipalScreen() }
        composable(Screen.About.route) { AboutScreen()
        }
        composable(Screen.Tourism.route) { TourismScreen() }
        composable(Screen.Community.route) { CommunityScreen() }
        composable(Screen.Support.route) { SupportScreen() }
        composable(Screen.CurrencyConverter.route) { CurrencyConverterScreen() }
        composable(Screen.Weather.route) { WeatherScreen() }
        composable(Screen.FavouriteMemories.route) { FavouriteMemoriesScreen() }
        composable(Screen.WhereToStay.route) { WhereToStayScreen() }


    }
}
