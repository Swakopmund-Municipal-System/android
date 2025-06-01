// Fixed API Service
package com.example.swakopmundapp.data.network

import com.example.swakopmundapp.data.model.Report.EnvironmentalReportRequest
import com.example.swakopmundapp.data.model.Report.EnvironmentalReportResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EnvironmentalApiService {
    @POST("/api/health/environment/{report_id}")
    suspend fun submitReport(
        @Path("report_id") reportId: String,
        @Body report: EnvironmentalReportRequest
    ): Response<EnvironmentalReportResponse>
}