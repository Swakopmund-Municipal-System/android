package com.example.swakopmundapp.data.model.tourism

import androidx.lifecycle.ViewModel
import com.example.swakopmundapp.R

class TourismViewModel : ViewModel() {
    val activities = listOf(
        TourismActivity("Boat Cruise", R.drawable.boat_cruise, "Enjoy a relaxing cruise."),
        TourismActivity("Parachuting", R.drawable.parachuting, "Thrilling aerial view!"),
        TourismActivity("Quad Biking", R.drawable.quad_biking, "Adventure in the dunes."),
        TourismActivity("Camel Riding", R.drawable.camel_riding, "Desert experience."),
        TourismActivity("Aquarium Visit", R.drawable.aquarium, "See marine life."),
        TourismActivity("Sky Diving", R.drawable.sky_diving, "Freefall excitement!")
    )

    fun getActivitiesByType(type: String): List<TourismActivity> {
        return activities.filter { it.name.contains(type, ignoreCase = true) }
    }
}