package com.example.swakopmundapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(

    @PrimaryKey(autoGenerate = false)
    var Id: String,
    var Email: String,
    var FirstName: String,
    var LastName: String,
    var HomeAddress: String?

)
