package com.example.swakopmundapp.service.remoteRepoImp

import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.service.api.Api
import com.example.swakopmundapp.service.helpers.safeApiCall
import com.example.swakopmundapp.service.remoteRepoInt.RemoteAuthenticationRepoInt

internal class RemoteAuthenticationRepoImp(
    private val api: Api
) : RemoteAuthenticationRepoInt {

    override suspend fun signup(signup: SignUpDto) = safeApiCall { api.signup(signup) }

    override suspend fun login(login: LoginDto) = safeApiCall { api.login(login) }

}