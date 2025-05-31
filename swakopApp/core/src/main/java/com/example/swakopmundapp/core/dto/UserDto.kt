package com.example.swakopmundapp.core.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDto(

    @SerializedName("email")
    var email: String

) : Serializable
