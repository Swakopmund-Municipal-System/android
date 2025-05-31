package com.example.swakopmundapp.repository

import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.repository.transaction.fetchFromApiTransaction
import com.example.swakopmundapp.service.remoteRepoInt.RemoteAuthenticationRepoInt
import kotlinx.coroutines.delay

class AuthenticationRepository(
    private val service: RemoteAuthenticationRepoInt,
) {

    suspend fun signup(signup: SignUpDto) = fetchFromApiTransaction(
        fetchFromApi = {
            delay(1_000)
            service.signup(signup)
        }
    )

    suspend fun login(login: LoginDto) = fetchFromApiTransaction(
        fetchFromApi = {
            delay(1_000)
            service.login(login)
        }
    )

}