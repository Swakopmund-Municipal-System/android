package com.example.swakopmundapp.ui.municipal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.swakopmundapp.R
import com.example.swakopmundapp.data.model.Report.EnvironmentalReportRequest
import com.example.swakopmundapp.ui.reportissue.ReportIssueScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.UUID

@Composable
fun ReportAnIssueScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    val viewModel: ReportIssueViewModel = koinViewModel()

    ReportAnIssueScreenContent(
        onBack = onBack,
        onSubmitReport = { reportId, request ->
            viewModel.submitReport(reportId, request)
        }
    )
}

@Composable
private fun ReportAnIssueScreenContent(
    onBack: () -> Unit,
    onSubmitReport: suspend (String, EnvironmentalReportRequest) -> Result<String>
) {
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(colorResource(id = R.color.bluebar)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Report An Issue",
                    color = Color.White,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Title Field
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter report title...") },
                    singleLine = true,
                    enabled = !isSubmitting
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Details Field
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    placeholder = { Text("Describe the environmental issue in detail...") },
                    minLines = 5,
                    maxLines = 8,
                    enabled = !isSubmitting
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (title.isNotBlank() && details.isNotBlank()) {
                            coroutineScope.launch {
                                isSubmitting = true
                                try {
                                    val reportId = UUID.randomUUID().toString()
                                    val request = EnvironmentalReportRequest(
                                        title = title.trim(),
                                        details = details.trim()
                                    )

                                    val result = onSubmitReport(reportId, request)

                                    if (result.isSuccess) {
                                        snackbarHostState.showSnackbar("Report submitted successfully!")
                                        // Clear form
                                        title = ""
                                        details = ""
                                    } else {
                                        snackbarHostState.showSnackbar("Failed to submit report: ${result.exceptionOrNull()?.message}")
                                    }
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Error: ${e.message}")
                                } finally {
                                    isSubmitting = false
                                }
                            }
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please fill in both title and details")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isSubmitting && title.isNotBlank() && details.isNotBlank()
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp),
                            color = Color.White
                        )
                    } else {
                        Text("Submit Report", fontSize = 18.sp)
                    }
                }
            }
        }

        // Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
