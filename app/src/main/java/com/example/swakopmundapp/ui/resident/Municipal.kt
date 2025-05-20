package com.example.swakopmundapp.ui.resident

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.ui.shared.MenuListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MunicipalScreen(navController: NavHostController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Municipal")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0277BD),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    if (navController != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val municipalItems = listOf(
                Pair(Icons.Default.AccountBalance, "Bank Details"),
                Pair(Icons.Default.Business, "Departments"),
                Pair(Icons.Default.ShoppingCart, "Procurement"),
                Pair(Icons.Default.Download, "Downloads"),
                Pair(Icons.Default.ReportProblem, "Report an Issue")
            )

            municipalItems.forEach { (icon, title) ->
                MenuListItem(
                    icon = icon,
                    title = title,
                    onClick = { /* Navigate to respective screens */ }
                )

                Divider(modifier = Modifier.padding(start = 56.dp, end = 16.dp))
            }
        }
    }
}