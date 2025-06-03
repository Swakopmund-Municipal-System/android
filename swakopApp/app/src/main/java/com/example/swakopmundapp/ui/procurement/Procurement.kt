package com.example.swakopmundapp.ui.procurement

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swakopmundapp.R
import androidx.core.net.toUri

/**
 * A single “sub‐item” inside a ProcurementSection.
 * If [url] is non-null, we treat it as a clickable PDF link.
 * If [url] is null, we render it as a plain group header (e.g. “Version 1”, “Version 2”).
 */
private data class ProcurementItem(
    val label: String,
    val url: String? = null
)

private data class ProcurementSection(
    val title: String,
    val items: List<ProcurementItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcurementScreen(
    onBack: () -> Unit
) {
    // Tracks which section is currently expanded (or null if none).
    var expandedSectionTitle by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // === Build a list of sections and items (replace URLs with real links) ===
    val sections = listOf(
        ProcurementSection(
            title = "Annual Procurement Plan",
            items = listOf(
                // Group header
                ProcurementItem(label = "Municipal Council Annual Procurement Plan 2024", url = "https://swakopmund.naftal.dev-imcon.com/assets/docs/MUNICIPAL-COUNCIL-OF-SWAKOPMUND-ANNUAL-PROCUREMENT-PLAN-2024_25.pdf"),
                ProcurementItem(label = "Revised APP", url = "https://swakopmund.naftal.dev-imcon.com/assets/docs/revised-APP.pdf"),
                ProcurementItem(label = "APP July 2023–June 2024 Version 1", url = "https://swakopmund.naftal.dev-imcon.com/assets/docs/Municipal-Council-of-Swakopmund-Annual-Procurement-Plan-July-2023-to-June-2024-Version-1.pdf"),
                ProcurementItem(label = "APP 2022–23 PPU", url = "https://swakopmund.naftal.dev-imcon.com/assets/docs/Municipality-of-Swakopmund-Annual-Procurement-Plan-2022_23-PPU.pdf")
            )
        ),
        ProcurementSection(
            title = "Bids & Requests for Sealed Quotations",
            items = listOf(
                ProcurementItem(label = "Development of Swakopmund Economic Barometer", url = "https://swakopmun.com/index/bid/121"),
                ProcurementItem(label = "Supply & Delivery of One Refuse Collector Compactor Truck", url = "https://swakopmun.com/index/bid/122"),
                ProcurementItem(label = "Rewind Two 22 kW Motors & a Digester Mixer", url = "https://swakopmun.com/index/bid/123"),
                ProcurementItem(label = "Supply & Delivery of 122 kW Grundfos Pump", url = "https://swakopmun.com/index/bid/126"),
                ProcurementItem(label = "Maintenance of Fire Safety Equipment & Tools", url = "https://swakopmun.com/index/bid/120"),
                ProcurementItem(label = "Consumer Tokens for Water Works", url = "https://swakopmun.com/index/bid/124"),
                ProcurementItem(label = "Supply & Delivery of 2.2 kW Pump for DRC Sports Field", url = "https://swakopmun.com/index/bid/125"),
                ProcurementItem(label = "Supply Rear Spring Blades, U‐Bolts & Centre Bolts", url = "https://swakopmun.com/index/bid/117"),
                ProcurementItem(label = "Supply & Delivery of Electric Demolition Hammer & Chisels", url = "https://swakopmun.com/index/bid/116"),
                ProcurementItem(label = "Supply & Delivery of Municipal Traffic Uniforms", url = "https://swakopmun.com/index/bid/118")
            )
        ),
        ProcurementSection(
            title = "Awarded Bids",
            items = listOf(
                ProcurementItem(label = "Notice of Award: Economic Barometer", url = "https://swakopmun.com/index/bid/121"),
                ProcurementItem(label = "Notice of Award: Refuse Collector Compactor Truck", url = "https://swakopmun.com/index/bid/122"),
                ProcurementItem(label = "Notice of Award: 22 kW Motors & Digester Mixer", url = "https://swakopmun.com/index/bid/123"),
                ProcurementItem(label = "Notice of Award: 122 kW Grundfos Pump", url = "https://swakopmun.com/index/bid/126"),
                // …add more awarded items here
            )
        ),
        ProcurementSection(
            title = "Cancelled Bids",
            items = listOf(
                // If no cancelled bids exist, you can leave this list empty.
                // ProcurementItem(label = "Some Cancelled Bid", url = "https://…/cancelled_bid.pdf"),
            )
        ),
        ProcurementSection(
            title = "Bid Opening Register",
            items = listOf(
                ProcurementItem(label = "Request for Proposal: Tourism Routes in Swakopmund", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Supply & Delivery of Fibreglass Grating Sheets", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Supply & Delivery of Roadmarking Paint", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Supply & Delivery of Electric Pallet Stacker", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Supply & Delivery of Hatchback Patrol Vehicle", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Supply & Delivery of Knife Gate Valves & Steel Pipes", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Supply & Delivery of Assorted Copy Papers", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Consultancy: Traffic Impact Study for ERF 5360", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Consultancy: Stormwater Management System", url = "https://swakopmun.com/index/external/9"),
                ProcurementItem(label = "Auctioneer for Obsolete Office/Household Equipment", url = "https://swakopmun.com/index/external/9")
            )
        ),
        ProcurementSection(
            title = "Notices",
            items = listOf(
                ProcurementItem(label = "Notice: Economic Barometer", url = "https://swakopmun.com/index/bid/121"),
                ProcurementItem(label = "Notice: Refuse Collector Compactor Truck", url = "https://swakopmun.com/index/bid/122"),
                ProcurementItem(label = "Notice: Digester Mixer & 22 kW Motors", url = "https://swakopmun.com/index/bid/123"),
                ProcurementItem(label = "Notice: Grundfos Pump", url = "https://swakopmun.com/index/bid/126"),
                ProcurementItem(label = "Notice: Fire Safety Equipment & Tools", url = "https://swakopmun.com/index/bid/120"),
                ProcurementItem(label = "Notice: Water Works Tokens", url = "https://swakopmun.com/index/bid/124"),
                ProcurementItem(label = "Notice: DRC Sports Field Pump", url = "https://swakopmun.com/index/bid/125"),
                ProcurementItem(label = "Notice: Rear Spring Blades, U‐Bolts & Centre Bolts", url = "https://swakopmun.com/index/bid/117"),
                ProcurementItem(label = "Notice: Electric Demolition Hammer & Chisels", url = "https://swakopmun.com/index/bid/116"),
                ProcurementItem(label = "Notice: Municipal Traffic Uniforms", url = "https://swakopmun.com/index/bid/118")
            )
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Procurement",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.bluebar)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(sections) { section ->
                SectionItem(
                    section = section,
                    isExpanded = (expandedSectionTitle == section.title),
                    onHeaderClick = { clickedTitle ->
                        expandedSectionTitle =
                            if (expandedSectionTitle == clickedTitle) null else clickedTitle
                    },
                    onPdfClick = { pdfUrl ->
                        // Launch the PDF or webpage in the browser/download manager:
                        val intent = Intent(Intent.ACTION_VIEW, pdfUrl.toUri())
                        context.startActivity(intent)
                    }
                )

                // White divider between sections
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
private fun SectionItem(
    section: ProcurementSection,
    isExpanded: Boolean,
    onHeaderClick: (String) -> Unit,
    onPdfClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        // --- Header Row ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.bluebar))
                .clickable { onHeaderClick(section.title) }
                .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = section.title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color.White
                )
            }
        }

        // --- Expanded Content ---
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE0F2F8)) // same light-blue background as DownloadsScreen
                    .padding(start = 24.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            ) {
                section.items.forEach { item ->
                    if (item.url == null) {
                        // This is a “group header”
                        Text(
                            text = item.label,
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                        )
                    } else {
                        // This is an actual PDF/link. Show icon + clickable row.
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onPdfClick(item.url) }
                                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Download,
                                contentDescription = "Download PDF",
                                tint = Color(0xFF0D47A1), // Darker blue tint
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = item.label,
                                fontSize = 14.sp,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun ProcurementScreenPreview() {
    ProcurementScreen(onBack = { /* no-op for preview */ })
}
