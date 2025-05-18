package com.example.swakopmundapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Municipal : Screen("municipal")
    object Auth : Screen("auth")
    object About : Screen("about")
    object TourismGrid : Screen("tourism_grid")
    object TourismDetail : Screen("tourism_detail/{activityName}") {
        fun createRoute(activityName: String) = "tourism_detail/$activityName"
    }

    object Community : Screen("community")
    object Support : Screen("support")
    object CurrencyConverter : Screen("currency")
    object Weather : Screen("weather")
    object FavouriteMemories : Screen("memories")
    object WhereToStay : Screen("stay")
    object Map : Screen("map")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object ForgotPassword : Screen("forgot_password")
    object Login : Screen("login")
    object ExchangeChart : Screen("exchange_chart")
    object Start : Screen("start")
    // Add others as needed
}
