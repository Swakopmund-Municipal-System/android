package com.example.swakopmundapp.di

import com.example.swakopmundapp.ui.currency.ExchangeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel // Koin's ViewModel DSL
import org.koin.dsl.module

val viewModelModule = module {
    // Define how to create an ExchangeViewModel
    // 'get()' tells Koin to resolve and inject com.example.swakopmundapp.data.network.OpenExchangeRatesApi
    viewModel { ExchangeViewModel(get()) }

    // You'll add other ViewModels here later, e.g.:
    // viewModel { AnotherViewModel(get(), get()) }
}