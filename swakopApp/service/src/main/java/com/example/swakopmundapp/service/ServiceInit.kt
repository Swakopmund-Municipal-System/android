package com.example.swakopmundapp.service

import com.example.swakopmundapp.service.client.BasicAuthInterceptor
import com.example.swakopmundapp.service.client.OkHttpClient
import com.example.swakopmundapp.service.client.RetrofitClient
import com.example.swakopmundapp.service.remoteRepoImp.RemoteAuthenticationRepoImp
import com.example.swakopmundapp.service.remoteRepoInt.RemoteAuthenticationRepoInt
import org.koin.dsl.module

val serviceModule = module {
    single { OkHttpClient(get()) }
    single { BasicAuthInterceptor() }
    single { RetrofitClient(get()).instance }

    single <RemoteAuthenticationRepoInt> { RemoteAuthenticationRepoImp(get()) }
}