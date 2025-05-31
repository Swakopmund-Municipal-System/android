package com.example.swakopmundapp.koin

import com.example.swakopmundapp.repository.AuthenticationRepository
import com.example.swakopmundapp.ui.currency.ExchangeViewModel
import com.example.swakopmundapp.viewModel.AuthenticationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthenticationRepository(get()) }
}

val viewModelModule = module {
    viewModel { AuthenticationViewModel(get()) }
    viewModel { ExchangeViewModel(get()) }
}