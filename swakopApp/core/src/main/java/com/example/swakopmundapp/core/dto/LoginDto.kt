package com.example.swakopmundapp.core.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginDto(

    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String

) : Serializable