package com.example.swakopmundapp.service.remoteRepoInt

import com.example.swakopmundapp.service.helpers.ServiceResponse

interface RemoteUserRepoInt {

    fun initToken(token: String)

    suspend fun logout(): ServiceResponse<Any>

}