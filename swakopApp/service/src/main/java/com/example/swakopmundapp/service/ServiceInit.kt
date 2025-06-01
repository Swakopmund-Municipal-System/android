package com.example.swakopmundapp.service

import com.example.swakopmundapp.service.client.BasicAuthInterceptor
import com.example.swakopmundapp.service.client.OkHttpClient
import com.example.swakopmundapp.service.client.RetrofitClient
import com.example.swakopmundapp.service.remoteRepoImp.RemoteAuthenticationRepoImp
import com.example.swakopmundapp.service.remoteRepoImp.RemoteUserRepoImp
import com.example.swakopmundapp.service.remoteRepoInt.RemoteAuthenticationRepoInt
import com.example.swakopmundapp.service.remoteRepoInt.RemoteUserRepoInt
import org.koin.dsl.module

val serviceModule = module {
    single { OkHttpClient(get()) }
    single { BasicAuthInterceptor() }
    single { RetrofitClient(get()).instance }

    single <RemoteAuthenticationRepoInt> { RemoteAuthenticationRepoImp(get()) }
    single <RemoteUserRepoInt> { RemoteUserRepoImp(get(), get()) }
}