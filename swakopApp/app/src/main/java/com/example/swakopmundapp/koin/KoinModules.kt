package com.example.swakopmundapp.koin

import com.example.swakopmundapp.data.model.tourism.TourismViewModel
import com.example.swakopmundapp.data.repository.TourismRepository
import com.example.swakopmundapp.repository.AuthenticationRepository
import com.example.swakopmundapp.repository.UserRepository
import com.example.swakopmundapp.ui.currency.ExchangeViewModel
import com.example.swakopmundapp.ui.municipal.ReportIssueViewModel
import com.example.swakopmundapp.ui.weather.WeatherViewModel
import com.example.swakopmundapp.viewModel.AuthenticationViewModel
import com.example.swakopmundapp.viewModel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthenticationRepository(get(), get()) }
    single { UserRepository(get()) }
    single { TourismRepository(get()) }
}

val viewModelModule = module {
    viewModel { AuthenticationViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { ExchangeViewModel(get()) }
    viewModel { TourismViewModel(get()) }
    viewModel { ReportIssueViewModel(get()) }
    viewModel { WeatherViewModel(get())}

}

