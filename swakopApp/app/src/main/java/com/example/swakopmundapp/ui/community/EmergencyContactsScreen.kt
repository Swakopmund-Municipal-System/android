package com.example.swakopmundapp.ui.community

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class EmergencyContact(
    val title: String,
    val number: String,
    val description: String,
    val icon: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyContactsScreen(navController: NavHostController? = null) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Contacts") },
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
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            emergencyContacts.forEach { contact ->
                EmergencyContactCard(
                    contact = contact,
                    onCopyClick = { copyToClipboard(context, contact.number) }
                )
            }
        }
    }
}

@Composable
private fun EmergencyContactCard(
    contact: EmergencyContact,
    onCopyClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD) // Light blue background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                contact.icon()
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0277BD)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onCopyClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Phone",
                            tint = Color(0xFF0277BD),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = contact.number,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF0277BD)
                        )
                    }

                    IconButton(
                        onClick = onCopyClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = Color(0xFF0277BD),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = contact.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Emergency Number", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Number copied to clipboard", Toast.LENGTH_SHORT).show()
}

private val emergencyContacts = listOf(
    EmergencyContact(
        title = "Police Emergency",
        number = "10111",
        description = "For immediate police assistance and emergency response",
        icon = {
            Icon(
                imageVector = Icons.Default.LocalPolice,
                contentDescription = "Police",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    EmergencyContact(
        title = "Ambulance",
        number = "081 124",
        description = "Medical emergency services and ambulance dispatch",
        icon = {
            Icon(
                imageVector = Icons.Default.LocalHospital,
                contentDescription = "Ambulance",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    EmergencyContact(
        title = "Fire Department",
        number = "081 122",
        description = "Fire emergencies and rescue services",
        icon = {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = "Fire",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    EmergencyContact(
        title = "Swakopmund Hospital",
        number = "064 410 4000",
        description = "Main hospital emergency services",
        icon = {
            Icon(
                imageVector = Icons.Default.LocalHospital,
                contentDescription = "Hospital",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    EmergencyContact(
        title = "Coastal Health",
        number = "064 400 950",
        description = "Private medical emergency services",
        icon = {
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = "Health",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    EmergencyContact(
        title = "Namibian Coast Guard",
        number = "064 205 000",
        description = "Maritime emergencies and coastal rescue",
        icon = {
            Icon(
                imageVector = Icons.Default.DirectionsBoat,
                contentDescription = "Coast Guard",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    ),
    EmergencyContact(
        title = "Tourist Police",
        number = "081 968 0029",
        description = "Specialized assistance for tourists",
        icon = {
            Icon(
                imageVector = Icons.Default.People,
                contentDescription = "Tourist Police",
                tint = Color(0xFF0277BD),
                modifier = Modifier.size(32.dp)
            )
        }
    )
)
