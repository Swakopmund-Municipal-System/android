package com.example.swakopmundapp.ui.map

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.BottomNavBar
import com.example.swakopmundapp.ui.shared.TopBlueBar
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraBoundsOptions
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CoordinateBounds
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

@Composable
fun MapScreen(navController: NavHostController) {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()
    var mapboxMapInstance by remember { mutableStateOf<MapboxMap?>(null) }

    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<SearchItem>>(emptyList()) }
    val allLocations = remember { mutableStateListOf<SearchItem>() }

    LaunchedEffect(mapView) {
        val map = mapView.getMapboxMap()
        mapboxMapInstance = map

        map.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(14.5272, -22.6792))
                .pitch(0.0)
                .zoom(14.0)
                .bearing(0.0)
                .build()
        )

        map.loadStyle(Style.MAPBOX_STREETS) { style ->
            addGeoJsonLayersFromAssets(context, style)
            restrictToSwakopmund(map)
            configureTourismGestures(mapView)

            val fileNames = listOf(
                "swakop-landmarks.geojson",
                "swakop-history.geojson",
                "swakop-exhibitions.geojson",
                "swakop-dune-7.geojson",
                "swakop-aquarium.geojson",
                "swakop-crystal-gallery.geojson"
            )

            fileNames.forEach { fileName ->
                try {
                    val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
                    val featureCollection = FeatureCollection.fromJson(json)
                    val items = featureCollection.features()?.mapNotNull { feature ->
                        val title = feature.getStringProperty("title")
                        val geometry = feature.geometry() as? Point
                        if (geometry != null && title != null) SearchItem(title, geometry) else null
                    } ?: emptyList()
                    allLocations.addAll(items)
                } catch (e: Exception) {
                    Log.e("MapSearch", "Error reading $fileName", e)
                }
            }

            Handler(Looper.getMainLooper()).postDelayed({
                mapboxMapInstance?.let { animateToLighthouse(it) }
            }, 5000)
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. The map view (background)
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )

            // 2. Search bar (foreground, top-aligned)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        searchResults = if (it.isBlank()) emptyList()
                        else allLocations.filter { item -> item.title.contains(it, ignoreCase = true) }
                    },
                    label = { Text("Search Places") },
                    modifier = Modifier.fillMaxWidth()
                )

                searchResults.take(5).forEach { result ->
                    Text(
                        text = result.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                mapboxMapInstance?.easeTo(
                                    CameraOptions.Builder()
                                        .center(result.point)
                                        .zoom(17.0)
                                        .build(),
                                    MapAnimationOptions.Builder().duration(2000).build()
                                )
                                searchQuery = ""
                                searchResults = emptyList()
                            }
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }

    }
}

data class SearchItem(val title: String, val point: Point)

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, mapView) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                Lifecycle.Event.ON_PAUSE -> mapView.onLowMemory()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            mapView.onDestroy()
        }
    }
    return mapView
}

private fun addGeoJsonLayersFromAssets(context: Context, style: Style) {
    fun loadAndAddLayer(fileName: String, sourceId: String, layerId: String, icon: String, size: Double, textColorHex: String, titleField: String = "{title}") {
        try {
            val geoJsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            style.addSource(geoJsonSource(sourceId) { data(geoJsonString) })
            style.addLayer(symbolLayer(layerId, sourceId) {
                iconImage(icon)
                iconSize(size)
                textField(titleField)
                textSize(12.0)
                textOffset(listOf(0.0, -(size * 0.8)))
                textColor(android.graphics.Color.parseColor(textColorHex))
                textHaloColor(android.graphics.Color.WHITE)
                textHaloWidth(1.0)
                iconAllowOverlap(true)
                textAllowOverlap(true)
            })
            Log.d("GeoJSON", "Loaded $fileName successfully.")
        } catch (e: Exception) {
            Log.e("GeoJSON", "Error loading $fileName: ${e.message}", e)
        }
    }

    loadAndAddLayer("swakop-landmarks.geojson", "landmarks-source", "landmarks-layer", "marker-15", 1.5, "#000000")
    loadAndAddLayer("swakop-history.geojson", "history-source", "history-layer", "monument-15", 1.3, "#8B4513")
    loadAndAddLayer("swakop-exhibitions.geojson", "exhibitions-source", "exhibitions-layer", "museum-15", 1.4, "#4CAF50")
    loadAndAddLayer("swakop-dune-7.geojson", "dune7-source", "dune7-layer", "mountain-15", 2.0, "#FF9800")
    loadAndAddLayer("swakop-aquarium.geojson", "aquarium-source", "aquarium-layer", "aquarium-15", 1.4, "#2196F3")
    loadAndAddLayer("swakop-crystal-gallery.geojson", "gallery-source", "gallery-layer", "art-gallery-15", 1.4, "#9C27B0")
}

private fun restrictToSwakopmund(mapboxMap: MapboxMap) {
    val swakopmundBounds = CameraBoundsOptions.Builder()
        .bounds(CoordinateBounds(Point.fromLngLat(14.50, -22.98), Point.fromLngLat(14.60, -22.65), false))
        .minZoom(12.0)
        .maxZoom(19.0)
        .build()
    mapboxMap.setBounds(swakopmundBounds)
}

private fun configureTourismGestures(mapView: MapView) {
    mapView.gestures.apply {
        rotateEnabled = false
        pitchEnabled = false
    }
}

private fun animateToLighthouse(mapboxMap: MapboxMap) {
    mapboxMap.easeTo(
        CameraOptions.Builder()
            .center(Point.fromLngLat(14.52421545092527, -22.67591155938082))
            .zoom(17.5)
            .build(),
        MapAnimationOptions.Builder().duration(2000).build()
    )
}
