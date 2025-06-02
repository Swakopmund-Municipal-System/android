package com.example.swakopmundapp.ui.tourism

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swakopmundapp.R
import com.example.swakopmundapp.data.model.tourism.ApiActivity
import com.example.swakopmundapp.data.model.tourism.TourismViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TourismGridScreen(
    navController: NavController,
    viewModel: TourismViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val activities by viewModel.activities.collectAsStateWithLifecycle()
    val isLoadingActivities by viewModel.isLoadingActivities.collectAsStateWithLifecycle()
    val errorActivities by viewModel.errorActivities.collectAsStateWithLifecycle()

    val places by viewModel.places.collectAsStateWithLifecycle()
    val isPlacesLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val placesError by viewModel.error.collectAsStateWithLifecycle()

    var searchTerm by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var selectedActivityForSheet by remember { mutableStateOf<ApiActivity?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Explore Swakopmund", color = Color.White, fontSize = 20.sp) },
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 12.dp)
            )

            Column(modifier = Modifier.fillMaxSize()) {

                // --- Activities Section ---
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Activities",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    when {
                        isLoadingActivities && activities.isEmpty() -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        errorActivities != null && activities.isEmpty() -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = errorActivities ?: "Failed to load activities.",
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        activities.isEmpty() && !isLoadingActivities -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (searchTerm.isNotBlank()) "No activities found for '$searchTerm'." else "No activities available at the moment.",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        else -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(bottom = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(activities, key = { activity -> "apiActivity-${activity.id}" }) { activityItem ->
                                    ActivityItemCard(
                                        activity = activityItem,
                                        onClick = {
                                            selectedActivityForSheet = activityItem
                                            scope.launch { sheetState.show() }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    if (isLoadingActivities && activities.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    } else if (errorActivities != null && activities.isNotEmpty() && !isLoadingActivities) {
                        Text(
                            text = errorActivities ?: "Error updating activities.",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        )
                    }
                } // End of Activities Section Column

                // Spacer between sections
                Spacer(modifier = Modifier.height(16.dp))

                // --- Places to Visit Section ---
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    if (places.isNotEmpty() || isPlacesLoading || placesError != null) {
                        Text(
                            text = "Places to Visit",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    when {
                        isPlacesLoading && places.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).heightIn(min = 100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        placesError != null && places.isEmpty() -> {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp).heightIn(min = 100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = placesError ?: "Failed to load places.",
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        places.isEmpty() && !isPlacesLoading -> {
                            // No message shown
                        }
                        places.isNotEmpty() -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(bottom = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 300.dp)
                            ) {
                                items(places, key = { place -> "place-${place.name}" }) { place ->
                                    PlaceItemCard(
                                        placeName = place.name,
                                        placeLocation = place.location,
                                        onClick = {
                                            navController.navigate("tourism_detail/${place.name}")
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (isPlacesLoading && places.isNotEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }
                } // End of Places Section Column
                Spacer(modifier = Modifier.height(16.dp))
            } // End of Inner Column
        } // End of Main Content Column

        // Modal Bottom Sheet for Activity Details
        if (selectedActivityForSheet != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        selectedActivityForSheet = null
                    }
                },
                sheetState = sheetState,
            ) {
                ActivityDetailsSheetContent(
                    activity = selectedActivityForSheet!!,
                    onClose = {
                        scope.launch {
                            if (sheetState.isVisible) sheetState.hide()
                            selectedActivityForSheet = null
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ActivityItemCard(
    activity: ApiActivity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageUrl = activity.constructedImageUrl
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(180.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .build(),
                contentDescription = activity.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = activity.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                activity.description?.takeIf { it.isNotBlank() }?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun PlaceItemCard(
    placeName: String,
    placeLocation: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Text(
//                text = placeName,
//                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
//                textAlign = TextAlign.Center
//            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = placeLocation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun ActivityDetailsSheetContent(
    activity: ApiActivity,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(top = 8.dp, bottom = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
                .width(40.dp)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f), RoundedCornerShape(2.dp))
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = activity.name,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                textAlign = TextAlign.Start
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = "Close details", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(activity.constructedImageUrl)
                .crossfade(true)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .build(),
            contentDescription = activity.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        activity.description?.takeIf { it.isNotBlank() }?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        activity.address?.takeIf { it.isNotBlank() }?.let {
            DetailItem(
                icon = Icons.Filled.LocationOn,
                text = it,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        activity.bookingUrl?.takeIf { it.isNotBlank() }?.let { url ->
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    try {
                        uriHandler.openUri(url)
                    } catch (e: Exception) {
                        println("Error opening URL: $url, Exception: ${e.message}")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Bookmark, contentDescription = "Booking Link Icon", modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Book Now / More Info")
            }
        }
        Spacer(modifier = Modifier.height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 8.dp))
    }
}

@Composable
private fun DetailItem(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}