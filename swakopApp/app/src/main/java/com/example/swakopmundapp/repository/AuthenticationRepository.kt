package com.example.swakopmundapp.repository

import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.database.localRepoInt.LocalProfileRepoInt
import com.example.swakopmundapp.repository.transaction.fetchSaveTransaction
import com.example.swakopmundapp.service.helpers.Status
import com.example.swakopmundapp.service.remoteRepoInt.RemoteAuthenticationRepoInt
import com.example.swakopmundapp.util.PREF_TOKEN
import kotlinx.coroutines.delay

class AuthenticationRepository(
    private val service: RemoteAuthenticationRepoInt,
    private val db: LocalProfileRepoInt
) {

    suspend fun signup(signup: SignUpDto) = fetchSaveTransaction(
        fetchFromApi = {
            delay(1_000)
            service.signup(signup)
        },

        save = { response ->
            if (response.status is Status.Success) {
                response.body?.let {
                    db.insertProfile(it)
                }
            }
        }
    )

    suspend fun login(login: LoginDto) = fetchSaveTransaction(
        fetchFromApi = {
            delay(1_000)
            service.login(login)
        },

        save = { response ->
            if (response.status is Status.Success) {
                PREF_TOKEN = response.body!!.token
            }
        }
    )

}