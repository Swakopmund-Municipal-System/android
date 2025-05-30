package com.example.swakopmundapp.ui.currency

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R // Make sure R.color.bluebar exists
import com.example.swakopmundapp.ui.navigation.Screen // Assuming this navigation definition exists
import org.koin.androidx.compose.koinViewModel // Koin's helper for Composable ViewModels

@SuppressLint("DefaultLocale") // For String.format
@OptIn(ExperimentalMaterial3Api::class) // For CenterAlignedTopAppBar, ExposedDropdownMenuBox
@Composable
fun CurrencyConverterScreen(
    navController: NavHostController,
    onBack: () -> Unit, // Changed to non-default to ensure it's provided
    viewModel: ExchangeViewModel = koinViewModel() // Inject ViewModel using Koin
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // Your flag map (can be kept or moved to ViewModel if it becomes dynamic)
    val flagMap = remember { // Use remember for static maps to avoid recomposition
        mapOf(
            "USD" to "\uD83C\uDDFA\uD83C\uDDF8", // 🇺🇸
            "EUR" to "\uD83C\uDDEA\uD83C\uDDFA", // 🇪🇺
            "GBP" to "\uD83C\uDDEC\uD83C\uDDE7", // 🇬🇧
            "NAD" to "\uD83C\uDDF3\uD83C\uDDE6", // 🇳🇦
            "ZAR" to "\uD83C\uDDFF\uD83C\uDDE6", // 🇿🇦
            "JPY" to "\uD83C\uDDEF\uD83C\uDDF5", // 🇯🇵
            "AUD" to "\uD83C\uDDE6\uD83C\uDDFA", // 🇦🇺
            "CAD" to "\uD83C\uDDE8\uD83C\uDDE6", // 🇨🇦
            "CHF" to "\uD83C\uDDE8\uD83C\uDDED", // 🇨🇭
            "CNY" to "\uD83C\uDDE8\uD83C\uDDF3", // 🇨🇳
            // Add more common flags as needed.
            // For a full solution, the API might provide this or you'd need a larger mapping.
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Currency Converter",
                        color = Color.White,
                        fontSize = 20.sp // Or use MaterialTheme.typography.titleLarge
                    )
                },
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
                    containerColor = colorResource(id = R.color.bluebar) // Ensure this color exists
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Make screen scrollable
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.isLoading && uiState.rates == null) { // Show initial loading
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 32.dp))
                Text("Fetching latest exchange rates...")
            }

            uiState.error?.let { errorMsg ->
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.errorContainer, MaterialTheme.shapes.small)
                        .padding(12.dp)
                )
            }

            // Only show conversion UI if not initial loading and no fatal error (or if rates are available despite minor error)
            if (uiState.rates != null || !uiState.isLoading) {
                OutlinedTextField(
                    value = uiState.amountToConvert,
                    onValueChange = { viewModel.onAmountChange(it) },
                    label = { Text("Amount to Convert") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done // Or ImeAction.Next if another field followed
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = { Text(uiState.fromCurrency, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 8.dp)) }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CurrencyExposedDropdown(
                        label = "From",
                        selectedCurrency = uiState.fromCurrency,
                        currencies = uiState.availableCurrencies,
                        flagMap = flagMap,
                        onCurrencySelected = {
                            viewModel.onFromCurrencyChange(it)
                            keyboardController?.hide()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isLoading // Disable while refreshing rates
                    )

                    IconButton(
                        onClick = {
                            viewModel.swapCurrencies()
                            keyboardController?.hide()
                        },
                        enabled = !uiState.isLoading,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Icons.Filled.SwapHoriz, contentDescription = "Swap currencies")
                    }

                    CurrencyExposedDropdown(
                        label = "To",
                        selectedCurrency = uiState.toCurrency,
                        currencies = uiState.availableCurrencies,
                        flagMap = flagMap,
                        onCurrencySelected = {
                            viewModel.onToCurrencyChange(it)
                            keyboardController?.hide()
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !uiState.isLoading // Disable while refreshing rates
                    )
                }

                if (uiState.isLoading && uiState.rates != null) { // Show loading indicator during refresh
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                }

                uiState.convertedAmount?.let { convertedValue ->
                    Text(
                        text = "Result: ${"%.2f".format(convertedValue)} ${uiState.toCurrency}",
                        style = MaterialTheme.typography.headlineMedium, // More prominent
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.fetchExchangeRates()
                        keyboardController?.hide()
                    },
                    enabled = !uiState.isLoading, // Disable button while loading
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Refreshing...")
                    } else {
                        Text("Refresh Rates")
                    }
                }

                // Optional: Navigation to chart screen
                Button(
                    onClick = {
                        navController.navigate(Screen.ExchangeChart.route)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Exchange Rate Chart")
                }

            } else if (!uiState.isLoading && uiState.error == null) {
                // This case might occur if API returns empty rates for some reason.
                Text("Currency rates are currently unavailable. Please try refreshing.")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyExposedDropdown(
    label: String,
    selectedCurrency: String,
    currencies: List<String>,
    flagMap: Map<String, String>,
    onCurrencySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val currentSelectionText = if (selectedCurrency.isNotEmpty() && currencies.contains(selectedCurrency)) {
        "${flagMap[selectedCurrency] ?: ""} $selectedCurrency"
    } else {
        "Select" // Placeholder if no valid selection or empty
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = !expanded }, // Only change if enabled
        modifier = modifier
    ) {
        OutlinedTextField(
            value = currentSelectionText,
            onValueChange = {}, // Value is display-only from state
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
                disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            ),
            enabled = enabled, // Control enabled state
            modifier = Modifier
                .menuAnchor() // This is important for ExposedDropdownMenuBox to position the menu
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded && enabled, // Menu itself only shows if enabled and expanded
            onDismissRequest = { expanded = false }
        ) {
            if (currencies.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Loading currencies...") },
                    onClick = { expanded = false },
                    enabled = false
                )
            } else {
                currencies.forEach { currencyCode ->
                    val currencyText = "${flagMap[currencyCode] ?: ""} $currencyCode"
                    DropdownMenuItem(
                        text = { Text(currencyText) },
                        onClick = {
                            onCurrencySelected(currencyCode)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}