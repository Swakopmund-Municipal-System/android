package com.example.swakopmundapp.ui.currency

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.ui.navigation.Screen
import com.example.swakopmundapp.ui.shared.TopBlueBar

@SuppressLint("DefaultLocale")
@Composable
fun CurrencyConverterScreen(
    navController: NavHostController,
    onBack: () -> Unit = {}
) {
    val currencies = listOf("USD", "EUR", "GBP", "NAD", "ZAR")

    val flagMap = mapOf(
        "USD" to "\uD83C\uDDFA\uD83C\uDDF8", // 🇺🇸
        "EUR" to "\uD83C\uDDEA\uD83C\uDDFA", // 🇪🇺
        "GBP" to "\uD83C\uDDEC\uD83C\uDDE7", // 🇬🇧
        "NAD" to "\uD83C\uDDF3\uD83C\uDDE6", // 🇳🇦
        "ZAR" to "\uD83C\uDDFF\uD83C\uDDE6"  // 🇿🇦
    )

    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("NAD") }
    var amount by remember { mutableStateOf("1") }
    var convertedAmount by remember { mutableStateOf("18.70") }

    // Mock conversion rates
    val mockRates = mapOf(
        Pair("USD", "NAD") to 18.7,
        Pair("EUR", "NAD") to 20.1,
        Pair("GBP", "NAD") to 23.3,
        Pair("ZAR", "NAD") to 1.0,
        Pair("NAD", "USD") to 0.053
    )

    // Update conversion whenever input or selection changes
    LaunchedEffect(amount, fromCurrency, toCurrency) {
        val input = amount.toDoubleOrNull() ?: 0.0
        val rate = mockRates.getOrDefault(Pair(fromCurrency, toCurrency), 1.0)
        convertedAmount = String.format("%.2f", input * rate)
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Currency",
                    color = Color.White,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrencyDropdown(
                label = "From",
                selected = fromCurrency,
                options = currencies,
                flagMap = flagMap
            ) {
                fromCurrency = it
            }

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            CurrencyDropdown(
                label = "To",
                selected = toCurrency,
                options = currencies,
                flagMap = flagMap
            ) {
                toCurrency = it
            }

            OutlinedTextField(
                value = convertedAmount,
                onValueChange = {},
                label = { Text("Converted Amount") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                navController.navigate(Screen.ExchangeChart.route)
            }) {
                Text("Exchange Rate Chart")
            }
        }
    }

}

@Composable
fun CurrencyDropdown(
    label: String,
    selected: String,
    options: List<String>,
    flagMap: Map<String, String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${flagMap[selected] ?: ""} $selected")
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { currency ->
                DropdownMenuItem(
                    text = { Text("${flagMap[currency] ?: ""} $currency") },
                    onClick = {
                        onSelect(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}
