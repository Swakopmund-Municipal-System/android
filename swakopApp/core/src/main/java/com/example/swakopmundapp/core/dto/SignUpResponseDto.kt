package com.example.swakopmundapp.core.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpResponseDto(

    @SerializedName("email")
    var email: String,

    @SerializedName("first_name")
    var firstName: String,

    @SerializedName("last_name")
    var lastName: String,

    @SerializedName("home_address")
    var homeAddress: String

) : Serializable
