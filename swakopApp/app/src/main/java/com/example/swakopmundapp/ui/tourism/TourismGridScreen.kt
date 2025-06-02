package com.example.swakopmundapp.ui.tourism

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
//import androidx.privacysandbox.tools.core.generator.build
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swakopmundapp.R
import com.example.swakopmundapp.data.model.tourism.ApiActivity
import com.example.swakopmundapp.data.model.tourism.TourismViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TourismGridScreen(
    navController: NavController,
    viewModel: TourismViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val activities by viewModel.activities.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoadingActivities.collectAsStateWithLifecycle()
    val error by viewModel.errorActivities.collectAsStateWithLifecycle()
    val places by viewModel.places.collectAsStateWithLifecycle()
    val isPlacesLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val placesError by viewModel.error.collectAsStateWithLifecycle()

    var searchTerm by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Search Activities", color = Color.White, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.bluebar)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                    viewModel.onSearchTermChanged(it)
                },
                label = { Text("Search Activities") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                trailingIcon = {
                    if (searchTerm.isNotEmpty()) {
                        IconButton(onClick = {
                            searchTerm = ""
                            viewModel.onSearchTermChanged(null)
                            focusManager.clearFocus()
                        }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Clear Search")
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.onSearchTermChanged(searchTerm.ifBlank { null })
                    focusManager.clearFocus()
                }),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            when {
                isLoading && activities.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null && activities.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = error ?: "An unknown error occurred",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                activities.isEmpty() && !isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (searchTerm.isNotBlank()) "No activities found for '$searchTerm'." else "Type to search activities.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(activities, key = { activity -> "apiActivity-${activity.id}" }) { activityItem ->
                            ActivityItemCard(
                                activity = activityItem,
                                onClick = {
                                    println("Clicked Activity: ${activityItem.name}")
                                }
                            )
                        }
                    }
                }
            }

            if (isLoading && activities.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            } else if (error != null && activities.isNotEmpty() && !isLoading) {
                Text(
                    text = error ?: "Error updating activities.",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            if (places.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Places to Visit",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.Start)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(places, key = { place -> "place-${place.name}" }) { place ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("tourism_detail/${place.name}")
                                }
                                .padding(8.dp)
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 2
                            )
                            Text(
                                text = place.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            if (isPlacesLoading && places.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            } else if (placesError != null && places.isEmpty()) {
                Text(
                    text = placesError ?: "Failed to load places.",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}

// ... (imports and TourismGridScreen composable)

@Composable
fun ActivityItemCard(
    activity: ApiActivity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Construct the image URL directly if imageId is available
    // OR use the helper property from ApiActivity
    // val imageUrl = activity.imageId?.let { "http://196.216.167.82/api/activities/images/$it" }
    val imageUrl = activity.constructedImageUrl // Using the helper property

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl) // Pass the constructed URL or null
                    .crossfade(true)
                    .placeholder(R.drawable.image_placeholder) // Your placeholder drawable
                    .error(R.drawable.image_placeholder)   // Your error drawable
                    .build(),
                contentDescription = activity.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp) // Or your desired height
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = activity.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                activity.description?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
