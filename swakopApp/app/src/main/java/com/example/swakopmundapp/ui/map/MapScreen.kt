package com.example.swakopmundapp.ui.map

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.swakopmundapp.data.model.map.Activity
import com.example.swakopmundapp.data.repository.ActivitiesRepository
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar
import com.example.swakopmundapp.ui.shared.TopBlueBar
import com.google.gson.JsonObject
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.gestures.gestures
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MapScreen(navController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val mapView = remember { MapView(context) }
    var mapboxMapInstance by remember { mutableStateOf<MapboxMap?>(null) }
    val repository = remember { ActivitiesRepository() }

    // Handle MapView lifecycle
    DisposableEffect(lifecycleOwner, mapView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                Lifecycle.Event.ON_PAUSE -> mapView.onLowMemory()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }

    // Map initialization
    LaunchedEffect(mapView) {
        val map = mapView.getMapboxMap()
        mapboxMapInstance = map

        map.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(14.5272, -22.6792))
                .zoom(14.0)
                .build()
        )

        map.loadStyleUri(Style.MAPBOX_STREETS) { style ->
            configureGestures(mapView)
            loadActivitiesFromBackend(context, style, repository, coroutineScope)

            Handler(Looper.getMainLooper()).postDelayed({
                animateToLighthouse(map)
            }, 3000)
        }
    }

    Scaffold(
        topBar = { TopBlueBar(title = "Map") },
        bottomBar = {
            BottomNavBar(
                currentRoute = Screen.Map.route,
                navController = navController
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            AndroidView(factory = { mapView }, modifier = Modifier.fillMaxSize())
        }
    }
}

private fun animateToLighthouse(map: MapboxMap) {
    map.easeTo(
        CameraOptions.Builder()
            .center(Point.fromLngLat(14.524215, -22.675911))
            .zoom(17.5)
            .build(),
        MapAnimationOptions.Builder().duration(2000).build()
    )
}

private fun configureGestures(mapView: MapView) {
    mapView.gestures.apply {
        rotateEnabled = false
        pitchEnabled = false
    }
}

private fun loadActivitiesFromBackend(
    context: android.content.Context,
    style: Style,
    repository: ActivitiesRepository,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        repository.searchActivitiesByLocation(-22.6792, 14.5272, 10.0).fold(
            onSuccess = { activities ->
                if (activities.isNotEmpty()) {
                    addActivitiesToMap(style, activities)
                    Toast.makeText(context, "Loaded ${activities.size} activities", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "No activities found", Toast.LENGTH_SHORT).show()
                }
            },
            onFailure = { error ->
                error.printStackTrace()
                Toast.makeText(context, "Error loading activities: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
    }
}

private fun addActivitiesToMap(style: Style, activities: List<Activity>) {
    val features = activities.mapNotNull { activity ->
        if (activity.latitude == 0.0 && activity.longitude == 0.0) return@mapNotNull null
        Feature.fromGeometry(
            Point.fromLngLat(activity.longitude, activity.latitude),
            JsonObject().apply {
                addProperty("id", activity.id)
                addProperty("name", activity.name)
                addProperty("description", activity.description ?: "")
                addProperty("type", activity.type ?: "activity")
                addProperty("priceRange", activity.priceRange ?: "unknown")
                addProperty("rating", activity.rating ?: 0.0)
                addProperty("duration", activity.duration ?: 0)
            }
        )
    }

    if (features.isNotEmpty()) {
        style.addSource(
            geoJsonSource("activities-source") {
                featureCollection(FeatureCollection.fromFeatures(features))
            }
        )

        style.addLayer(
            symbolLayer("activities-layer", "activities-source") {
                iconImage("attraction-15")
                iconSize(1.6)
                iconColor(Color.parseColor("#E91E63"))
                textField("{name}")
                textSize(11.0)
                textOffset(listOf(0.0, -1.8))
                textColor(Color.parseColor("#E91E63"))
                textHaloColor(Color.WHITE)
                textHaloWidth(1.0)
            }
        )
    }
}
