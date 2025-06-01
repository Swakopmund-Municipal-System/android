package com.example.swakopmundapp.repository

import com.example.swakopmundapp.repository.transaction.fetchFromApiTransaction
import com.example.swakopmundapp.service.remoteRepoInt.RemoteUserRepoInt
import com.example.swakopmundapp.util.PREF_TOKEN
import kotlinx.coroutines.delay

class UserRepository(
    private val service: RemoteUserRepoInt,
) {

    init {
        service.initToken(
            token = PREF_TOKEN
        )
    }

    suspend fun logout() = fetchFromApiTransaction(
        fetchFromApi = {
            delay(1_000)
            service.logout()
        }
    )

    fun clearServiceInterceptor() {
        service.initToken(token = "")
    }

}