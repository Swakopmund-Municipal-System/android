package com.example.swakopmundapp.service.remoteRepoImp

import com.example.swakopmundapp.service.api.Api
import com.example.swakopmundapp.service.client.BasicAuthInterceptor
import com.example.swakopmundapp.service.helpers.safeApiCall
import com.example.swakopmundapp.service.remoteRepoInt.RemoteUserRepoInt

internal class RemoteUserRepoImp(
    private val api: Api,
    private val interceptor: BasicAuthInterceptor
) : RemoteUserRepoInt {

    override fun initToken(token: String) {
        if (token.isNotBlank()) {
            interceptor.setToken(token)
        }
    }

    override suspend fun logout() = safeApiCall {
        api.logout()
    }

}