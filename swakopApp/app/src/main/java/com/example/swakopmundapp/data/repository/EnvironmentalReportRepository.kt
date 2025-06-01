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
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}