package com.example.swakopmundapp.ui.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swakopmundapp.R

// Data class for contact
data class EmergencyContact(val department: String, val contactName: String, val number: String)

// Updated Emergency Contact Screen with Material Icons
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactsScreen(navController: NavHostController) {
    val contacts = listOf(
        EmergencyContact("Fire Brigade", "Chief Joe (Walvis)", "081 234 4561"),
        EmergencyContact("Ambulance", "St. John Medics", "081 456 7890"),
        EmergencyContact("Police", "Inspector Ndeshi", "081 999 2222"),
        EmergencyContact("Coast Guard", "Captain Mvula", "081 333 4444")
    )

    // Mapping icons to departments
    val departmentIcons = mapOf(
        "Fire Brigade" to Icons.Default.LocalFireDepartment,
        "Ambulance" to Icons.Default.MedicalServices,
        "Police" to Icons.Default.Security,
        "Coast Guard" to Icons.Default.DirectionsBoat
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(colorResource(id = R.color.bluebar))
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Emergency Contacts",
                    color = Color.White,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(contacts) { contact ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = departmentIcons[contact.department] ?: Icons.Default.Phone,
                            contentDescription = null,
                            tint = Color(0xFF0277BD),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(contact.department, fontWeight = FontWeight.Bold)
                            Text(contact.contactName)
                            Text(contact.number, color = Color(0xFF0277BD), fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

