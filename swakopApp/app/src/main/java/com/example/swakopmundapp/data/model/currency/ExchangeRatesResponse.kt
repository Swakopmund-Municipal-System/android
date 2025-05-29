package com.example.swakopmundapp.data.model.currency

data class ExchangeRatesResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Double>  // Map of currency codes to their rates
)
