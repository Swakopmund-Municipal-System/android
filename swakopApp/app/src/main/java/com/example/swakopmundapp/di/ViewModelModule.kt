package com.example.swakopmundapp.di

import com.example.swakopmundapp.ui.currency.ExchangeViewModel
import com.example.swakopmundapp.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // Currency ViewModel
    viewModel { ExchangeViewModel(get()) } // Koin resolves OpenExchangeRatesApi

    // Weather ViewModel
    viewModel { WeatherViewModel(get()) }
}