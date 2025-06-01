package com.example.swakopmundapp.data.model.Report

import com.google.gson.annotations.SerializedName

data class EnvironmentalReportResponse(
    @SerializedName("report_id") val reportId: String,
    @SerializedName("created_at") val createdAt: String
)
