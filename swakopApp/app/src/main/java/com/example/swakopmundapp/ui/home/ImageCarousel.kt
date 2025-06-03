package com.example.swakopmundapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ImageCarousel(images: List<Int>) {
    var currentImageIndex by remember { mutableStateOf(0) } // State for current image index

    // Automatically change image every 3 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentImageIndex = (currentImageIndex + 1) % images.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = painterResource(id = images[currentImageIndex]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Indicators (Dots)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { i ->
                Box(
                    Modifier
                        .size(8.dp) // Dot size
                        .padding(horizontal = 3.dp)
                        .background(
                            color = if (i == currentImageIndex) Color.White else Color.Gray.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

