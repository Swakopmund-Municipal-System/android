package com.example.swakopmundapp.ui.municipal

import androidx.lifecycle.ViewModel
import com.example.swakopmundapp.data.model.Report.EnvironmentalReportRequest
import com.example.swakopmundapp.data.repository.EnvironmentalReportRepository

class ReportIssueViewModel(
    private val repository: EnvironmentalReportRepository
) : ViewModel() {

    suspend fun submitReport(reportId: String, request: EnvironmentalReportRequest): Result<String> {
        return try {
            val result = repository.submitReport(reportId, request)
            if (result.isSuccess) {
                Result.success(result.getOrNull()?.reportId ?: "Unknown")
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}