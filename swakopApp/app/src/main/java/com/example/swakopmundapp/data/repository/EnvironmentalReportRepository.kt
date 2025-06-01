package com.example.swakopmundapp.data.repository

import com.example.swakopmundapp.data.model.Report.EnvironmentalReportRequest
import com.example.swakopmundapp.data.model.Report.EnvironmentalReportResponse
import com.example.swakopmundapp.data.network.EnvironmentalApiService

class EnvironmentalReportRepository(
    private val apiService: EnvironmentalApiService
) {
    suspend fun submitReport(reportId: String, request: EnvironmentalReportRequest): Result<EnvironmentalReportResponse> {
        return try {
            val response = apiService.submitReport(reportId, request)
            when (response.code()) {
                201 -> {
                    response.body()?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("Empty response body"))
                }
                400 -> Result.failure(Exception("Invalid input - Please check your data"))
                401 -> Result.failure(Exception("Authentication required - Please check API credentials"))
                else -> Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.message}"))
        }
    }
}