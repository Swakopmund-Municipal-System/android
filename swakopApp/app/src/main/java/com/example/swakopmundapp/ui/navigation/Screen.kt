package com.example.swakopmundapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Municipal : Screen("municipal")
    object Auth : Screen("auth")
    object About : Screen("about")
    object Tourism : Screen("tourism")
    object Community : Screen("community")
    object Support : Screen("support")
    object CurrencyConverter : Screen("currency")
    object Weather : Screen("weather")
    object FavouriteMemories : Screen("memories")
    object WhereToStay : Screen("stay")
    // Add others as needed
}
