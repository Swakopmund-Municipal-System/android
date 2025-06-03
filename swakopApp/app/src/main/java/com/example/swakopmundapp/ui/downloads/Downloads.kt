package com.example.swakopmundapp.ui.downloads

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
import androidx.media3.exoplayer.offline.Download
import com.example.swakopmundapp.R

/**
 * A single “sub‐item” inside a DownloadSection.
 * If [url] is non-null, we treat it as a clickable PDF link.
 * If [url] is null, we render it as a plain group header.
 */
private data class DownloadItem(
    val label: String,
    val url: String? = null
)

private data class DownloadSection(
    val title: String,
    val items: List<DownloadItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    onBack: () -> Unit
) {
    // Holds the title of whichever section is currently expanded (or null if none).
    var expandedSectionTitle by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // === Build sections ===
    val sections = listOf(
        DownloadSection(
            title = "Animal Control",
            items = listOf(
                DownloadItem(
                    label = "By‐Law Control and Keeping of Dogs",
                    url = "https://swakopmun.com/uploads/files/xyktbd5o9wqp7ai.pdf"
                ),
                DownloadItem(
                    label = "Dog Registration Form",
                    url = "https://swakopmun.com/uploads/files/ruaxfvgw4et2nik.pdf"
                )
            )
        ),
        DownloadSection(
            title = "Business Registration",
            items = listOf(
                DownloadItem(
                    label = "Business Registration Form",
                    url = "https://swakopmun.com/uploads/files/mqfypdh78li4kzg.pdf"
                )
            )
        ),
        DownloadSection(
            title = "Corporate Services & Human Capital",
            items = listOf(
                // Group Header:
                DownloadItem(label = "Information Documents", url = null),
                // Actual PDF link under that group:
                DownloadItem(
                    label = "2025 Jan Condition of the Sale ‒ 4 Business Erven (Properties)",
                    url = "https://swakopmun.com/uploads/files/hbf6rz18eikdwl0.pdf"
                ),

                DownloadItem(label = "Application Forms", url = null),

                DownloadItem(
                    label = "Application Form for Assessment Rates Exemption ‒ Properties 2025",
                    url = "https://swakopmun.com/uploads/files/470j8f6gxteowmy.pdf"
                ),
                DownloadItem(
                    label = "Employment Application Form",
                    url = "https://swakopmun.com/uploads/files/hnycvx4sl73t568.pdf"
                )
            )
        ),
        DownloadSection(
            title = "Council Agenda",
            items = listOf(
                DownloadItem(
                    label = "Council Agenda (Part 3) 31 October 2024",
                    url = "https://swakopmun.com/uploads/files/jb62n3e48wvia9h.pdf"
                )
            )
        ),
        DownloadSection(
            title = "Engineering & Planning Services",
            items = listOf(
                // — First group header: “Application Forms” —
                DownloadItem(label = "Application Forms", url = null),
                DownloadItem(
                    label = "APPLICATION FORM FOR THE RELAXATION OF DEVELOPMENT STANDARDS",
                    url = "https://swakopmun.com/uploads/files/yazs1jilxor6hwm.pdf"
                ),
                DownloadItem(
                    label = "APPLICATION FORM FOR MUNICIPAL BOUNDARY WALL ADVERTISING",
                    url = "https://swakopmun.com/uploads/files/946es2uhbfjwmt3.pdf"
                ),
                DownloadItem(
                    label = "Appointment of Professional Engineer/other competent person",
                    url = "https://swakopmun.com/uploads/files/fpg8j_r7aym531k.pdf"
                ),
                DownloadItem(
                    label = "Compliance Application Form",
                    url = "https://swakopmun.com/uploads/files/yfaclxne15bs280.pdf"
                ),
                DownloadItem(
                    label = "New Building Plan Application Form",
                    url = "https://swakopmun.com/uploads/files/ju823ogxpszfwl4.pdf"
                ),
                DownloadItem(
                    label = "STREET NAMING NOMINATION FORM",
                    url = "https://swakopmun.com/uploads/files/y16_c823zu5sqgn.pdf"
                ),

                // — Second group header: “Information Documents” —
                DownloadItem(label = "Information Documents", url = null),
                DownloadItem(
                    label = "Public Registration as a Resident Occupation",
                    url = "https://swakopmun.com/uploads/files/bk1y0p9cgie238j.pdf"
                ),
                DownloadItem(
                    label = "Accommodation Establishment Policy",
                    url = "https://swakopmun.com/uploads/files/xrmvuk8zw1l7sij.pdf"
                ),
                DownloadItem(
                    label = "Swakopmund Parks, Jetty, Mole, and Beach Regulations: Local Authorities Act, 1992",
                    url = "https://swakopmun.com/uploads/files/rjzslp56awmuqbt.pdf"
                ),
                DownloadItem(
                    label = "Town Planning Amendment Scheme No.12",
                    url = "https://swakopmun.com/uploads/files/l0adqutgfmxj12b.pdf"
                ),
                DownloadItem(
                    label = "TOWN PLANNING APPLICATION FEES",
                    url = "https://swakopmun.com/uploads/files/4ms8p1v0_6l7dje.pdf"
                )
            )
        ),
        DownloadSection(
            title = "Health Services & Solid Waste",
            items = listOf(
                DownloadItem(
                    label = "Bin Application Form",
                    url = "https://swakopmun.com/uploads/files/q4pjyia5rugdmhl.pdf"
                )
            )
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Downloads",
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
                        // Fire an Intent to open the PDF URL in browser/download manager:
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
                        context.startActivity(intent)
                    }
                )

                // White divider between sections:
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
    section: DownloadSection,
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
                    .background(Color(0xFFE0F2F8))
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
                        // This is an actual PDF link. Show icon + clickable row.
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
                                tint = Color(0xFF0D47A1), // Darker blue tint (optional)
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
private fun DownloadsScreenPreview() {
    DownloadsScreen(onBack = { /* no-op */ })
}