package com.example.swakopmundapp.service.remoteRepoInt

import com.example.swakopmundapp.service.helpers.ServiceResponse

interface RemoteUserRepoInt {

    suspend fun logout(): ServiceResponse<Any>

}