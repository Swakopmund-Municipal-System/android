package com.example.swakopmundapp.ui.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swakopmundapp.ui.shared.TopBlueBar

@Composable
fun ExchangeChartScreen(navController: androidx.navigation.NavController) {
    Scaffold(
        topBar = {
            TopBlueBar(
                title = "Community",
                navController = navController
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("USD to NAD conversion chart")
            Text("1 USD = 18.70 NAD")
            Text("Updated a few seconds ago")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("48H", "1W", "1M", "6M", "1Y", "5Y").forEach {
                    AssistChip(onClick = { /* future chart filter */ }, label = { Text(it) })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mock chart box (can use MPAndroidChart or Recharts in real app)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text("Graph goes here", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
