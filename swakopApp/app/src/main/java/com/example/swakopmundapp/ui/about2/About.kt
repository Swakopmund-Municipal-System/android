package com.example.swakopmundapp.ui.about2

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swakopmundapp.R


@Composable
fun AboutScreen(onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.about_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // 2) Top bar, also fixed
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(colorResource(id = R.color.bluebar)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "About",
                    color = Color.White,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // 3) Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // 3a) Spacer to push content below the hero image
                Spacer(modifier = Modifier.height(20.dp))

                // 3b) “We Are” header + paragraph
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
                            "WE ARE",
                            style = MaterialTheme.typography.titleMedium,
                            color = colorResource(id = R.color.bluebar)
                        )
                        Text(
                            "The Municipality of Swakopmund",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Welcome to the Municipality of Swakopmund! Our mission is to meet the needs of our residents and visitors through efficient service delivery and community‐focused initiatives. We strive to create an environment that fosters growth, respect, and collaboration among our team and the public. Our vision is to ensure safe, affordable, and sustainable services that benefit the entire community, promoting development and a high quality of life. At Swakopmund Municipality, we uphold values of excellence, teamwork, and integrity—always working toward a bright future for our town.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // 3c) Mission card
                InfoCard(
                    iconRes = R.drawable.rocket,
                    title   = "MISSION"
                ) {
                    Text(
                        "We provide safe, efficient, reliable, affordable services and infrastructure to all.",
                        textAlign = TextAlign.Center,
                        style     = MaterialTheme.typography.bodyMedium
                    )
                }

                // 3d) Vision card
                InfoCard(
                    iconRes = R.drawable.binoculars,
                    title   = "VISION"
                ) {
                    Text(
                        "To be a leading smart city providing excellent services to all.",
                        textAlign = TextAlign.Center,
                        style     = MaterialTheme.typography.bodyMedium
                    )
                }

                // 3e) Values card
                InfoCard(
                    iconRes = R.drawable.diamond,
                    title   = "VALUES"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(
                            "Integrity – We are people with justice, fairness, honesty & accountability.",
                            "Diversity & Inclusiveness – We are people who accept different backgrounds, cultures and have respect for different views.",
                            "Innovation – We are people with the ability to adapt to change, be proactive and look for better ways of doing things."
                        ).forEach { line ->
                            Text("• $line", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InfoCard(
    @DrawableRes iconRes: Int,
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified
            )
            Text(
                text      = title,
                style     = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(onBack = { })
}
