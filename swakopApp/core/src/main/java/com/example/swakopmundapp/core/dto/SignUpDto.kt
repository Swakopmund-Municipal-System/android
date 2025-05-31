package com.example.swakopmundapp.core.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignUpDto(

    @SerializedName("email")
    var email: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("first_name")
    var firstName: String,

    @SerializedName("last_name")
    var lastName: String,

    @SerializedName("home_address")
    var homeAddress: String,

    @SerializedName("user_type_names")
    var userType: List<String>?

) : Serializable