package com.example.swakopmundapp.core.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponseDto(

    @SerializedName("expiry")
    var expiryDate: String,

    @SerializedName("token")
    var token: String,

    @SerializedName("user")
    var userEmail: UserDto?

) : Serializable
