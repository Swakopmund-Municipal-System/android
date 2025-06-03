package com.example.swakopmundapp.database

import com.example.swakopmundapp.database.localRepoImp.LocalProfileRepoImp
import com.example.swakopmundapp.database.localRepoInt.LocalProfileRepoInt
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(get()) }
    single { AppDatabase.getInstance(get()).profileDao() }

    single<LocalProfileRepoInt> { LocalProfileRepoImp(get()) }

}