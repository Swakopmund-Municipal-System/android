package com.example.swakopmundapp.ui.tourism

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.swakopmundapp.data.model.tourism.TourismViewModel
import com.example.swakopmundapp.ui.shared.TopBlueBar

@Composable
fun TourismDetailScreen(
    navController: NavController,
    activityType: String,
    viewModel: TourismViewModel
) {
    val filtered = viewModel.getActivitiesByType(activityType)

    Scaffold(
        topBar = {
            TopBlueBar(
                title = "$activityType Activities",
                navController = navController
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {

            LazyColumn {
                items(filtered) { item ->
                    Row(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = item.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = item.name)
                            Text(text = item.description, maxLines = 2)
                            Button(onClick = { /* TODO: Explore click */ }) {
                                Text("Explore now")
                            }
                        }
                    }
                }
            }
        }
    }
}
