package com.example.swakopmundapp.data.model.Report

import com.google.gson.annotations.SerializedName

data class EnvironmentalReportRequest(
    @SerializedName("title") val title: String,
    @SerializedName("details") val details: String
)
