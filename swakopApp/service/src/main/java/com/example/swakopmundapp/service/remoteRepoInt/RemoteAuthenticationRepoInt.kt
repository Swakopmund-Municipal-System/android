package com.example.swakopmundapp.service.remoteRepoInt

import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.core.dto.LoginResponseDto
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.core.dto.SignUpResponseDto
import com.example.swakopmundapp.service.helpers.ServiceResponse

interface RemoteAuthenticationRepoInt {

    suspend fun signup(signup: SignUpDto): ServiceResponse<SignUpResponseDto>

    suspend fun login(login: LoginDto): ServiceResponse<LoginResponseDto>

}