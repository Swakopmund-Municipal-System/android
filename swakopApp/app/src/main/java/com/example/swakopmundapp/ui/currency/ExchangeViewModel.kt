package com.example.swakopmundapp.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// import androidx.preference.contains   FUUUUUUCCKCK YOU Preference
import com.example.swakopmundapp.data.model.currency.ExchangeRatesResponse // Ensure this import is present if not already
import com.example.swakopmundapp.data.network.OpenExchangeRatesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
// Data class to represent the state of the CurrencyConverterScreen UI
data class CurrencyConverterUiState(
    val rates: Map<String, Double>? = null,
    val availableCurrencies: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val amountToConvert: String = "1", // Default amount
    val fromCurrency: String = "USD",  // Default 'from' currency
    val toCurrency: String = "NAD",    // Default 'to' currency
    val convertedAmount: Double? = null
)

class ExchangeViewModel(
    private val openExchangeRatesApi: OpenExchangeRatesApi // Koin will inject this
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrencyConverterUiState(isLoading = true))
    val uiState: StateFlow<CurrencyConverterUiState> = _uiState.asStateFlow()

    private val appId = "aeaf2e96797e42bc9f4e76ea91345e6d" // Make sure this is your valid key

    init {
        fetchExchangeRates()
    }

    fun fetchExchangeRates() {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(isLoading = true, error = null) }
            try {
                // Get the Retrofit Response object
                val retrofitResponse: retrofit2.Response<ExchangeRatesResponse> = openExchangeRatesApi.getLatestRates(appId)

                if (retrofitResponse.isSuccessful) {
                    val exchangeData: ExchangeRatesResponse? = retrofitResponse.body() // Get the actual data from the body
                    if (exchangeData != null) {
                        val ratesMap = exchangeData.rates // Access rates from the body
                        val currencyKeys = ratesMap.keys.toList().sorted()

                        _uiState.update { currentState ->
                            currentState.copy(
                                rates = ratesMap,
                                availableCurrencies = currencyKeys,
                                isLoading = false,
                                fromCurrency = if (currencyKeys.contains(currentState.fromCurrency)) currentState.fromCurrency else currencyKeys.firstOrNull() ?: "USD",
                                toCurrency = if (currencyKeys.contains(currentState.toCurrency)) currentState.toCurrency else currencyKeys.find { it == "NAD" } ?: currencyKeys.drop(1).firstOrNull() ?: "EUR"
                            )
                        }
                        convertCurrency() // Perform initial conversion with fetched rates
                    } else {
                        // Body was null, API returned success but no data
                        _uiState.update { currentState -> currentState.copy(error = "API returned empty response body.", isLoading = false) }
                    }
                } else {
                    // API call was not successful (e.g., 401, 404, 500)
                    val errorBody = retrofitResponse.errorBody()?.string() ?: "No error details"
                    _uiState.update { currentState -> currentState.copy(error = "API Error: ${retrofitResponse.code()} - ${retrofitResponse.message()}. Details: $errorBody", isLoading = false) }
                }

            } catch (e: IOException) { // Handles network-related errors
                _uiState.update { currentState -> currentState.copy(error = "Network error. Please check your connection and try again.", isLoading = false) }
            } catch (e: Exception) { // Handles other errors (e.g., JSON parsing, API specific errors)
                _uiState.update { currentState -> currentState.copy(error = "Error fetching rates: ${e.message}", isLoading = false) }
            }
        }
    }

    fun onAmountChange(newAmount: String) {
        if (newAmount.isEmpty() || newAmount.matches(Regex("^\\d*\\.?\\d*\$"))) {
            _uiState.update { currentState -> currentState.copy(amountToConvert = newAmount) }
            convertCurrency()
        }
    }

    fun onFromCurrencyChange(newFromCurrency: String) {
        _uiState.update { currentState -> currentState.copy(fromCurrency = newFromCurrency) }
        convertCurrency()
    }

    fun onToCurrencyChange(newToCurrency: String) {
        _uiState.update { currentState -> currentState.copy(toCurrency = newToCurrency) }
        convertCurrency()
    }

    fun swapCurrencies() {
        _uiState.update { currentState ->
            currentState.copy(
                fromCurrency = currentState.toCurrency,
                toCurrency = currentState.fromCurrency
            )
        }
        convertCurrency() // Recalculate after swapping
    }

    private fun convertCurrency() {
        val state = _uiState.value
        val amount = state.amountToConvert.toDoubleOrNull()
        val currentRates = state.rates // This remains the same as rates are now correctly populated in _uiState

        if (amount == null || currentRates.isNullOrEmpty()) {
            _uiState.update { currentState -> currentState.copy(convertedAmount = null) }
            return
        }

        val fromRate = currentRates[state.fromCurrency]
        val toRate = currentRates[state.toCurrency]

        if (fromRate == null || toRate == null) {
            _uiState.update { currentState -> currentState.copy(convertedAmount = null, error = "Rate for one or both selected currencies is not available.") }
            return
        }
        if (fromRate == 0.0) {
            _uiState.update { currentState -> currentState.copy(convertedAmount = null, error = "Cannot convert from a currency with a rate of 0.") }
            return
        }

        val amountInBaseCurrency = amount / fromRate
        val finalAmount = amountInBaseCurrency * toRate

        _uiState.update { currentState -> currentState.copy(convertedAmount = finalAmount, error = null) }
    }
}