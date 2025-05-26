package com.example.swakopmundapp.ui.support

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swakopmundapp.R

@Composable
fun SupportScreen(onBack: () -> Unit = {}) {
    Image(
        painter = painterResource(id = R.drawable.support_page_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Standard app bar height
                .background(color = colorResource(id = R.color.bluebar))
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Support",
                color = Color.White,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Placeholder for the rest of the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = Color.White.copy(alpha = 1.0f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Contact Us",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.bluebar)
                    )
                    Text(
                        "Contact Us",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Phone: +264 61 123 4567",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Email: support@swakopmund.tourism.na",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Frequently Asked Questions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Are guided tours available?\n Yes, book in advance via the app.",

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Tips for Visitors",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text("• Bring sunscreen and water.")
                    Text("• Wear comfortable walking shoes.")
                    Text("• Check the weather before visiting.")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SupportScreenPreview(){
    SupportScreen()
}
