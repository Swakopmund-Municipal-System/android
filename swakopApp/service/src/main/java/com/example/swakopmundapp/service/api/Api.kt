package com.example.swakopmundapp.service.api

import com.example.swakopmundapp.core.dto.LoginDto
import com.example.swakopmundapp.core.dto.LoginResponseDto
import com.example.swakopmundapp.core.dto.SignUpDto
import com.example.swakopmundapp.core.dto.SignUpResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface Api {

    /**
     * AUTHENTICATION
     */
    @POST("auth/user/sign-up/")
    suspend fun signup(@Body signup: SignUpDto): Response<SignUpResponseDto>

    @POST("auth/user/login/")
    suspend fun login(@Body login: LoginDto): Response<LoginResponseDto>

    @POST("auth/user/logout/")
    suspend fun logout(): Response<Any>

}