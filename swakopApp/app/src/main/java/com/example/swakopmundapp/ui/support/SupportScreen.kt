package com.example.swakopmundapp.ui.support

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swakopmundapp.R
import androidx.compose.ui.platform.LocalContext



val BlueBarColor = Color(0xFF2196F3)
val LightSandyColor = Color(0xFFF5F5DC)

@Composable
fun SupportScreen(onBack: () -> Unit = {}) {
    Image(
        painter = painterResource(id = R.drawable.support_page_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(modifier = Modifier.fillMaxSize()) {
        // App Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = colorResource(id = R.color.bluebar))
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Support",
                color = Color.White,
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        // Thin divider below app bar
        Divider(color = LightSandyColor, thickness = 1.dp)



        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightSandyColor, RoundedCornerShape(8.dp)) 
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(8.dp)) 
                    .padding(12.dp) // Inner padding
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tip",
                        tint = colorResource(id = R.color.bluebar),
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        "Did you know? You can download maps for offline use in areas with poor connectivity!", // Example tip
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }


            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))



            Text(
                "Contact Us",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.bluebar),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val uriHandler = LocalUriHandler.current
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:+264811234567"))
                        context.startActivity(intent)
                    }
                    .padding(vertical = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone", modifier = Modifier.padding(end = 8.dp))
                Text(
                    "Phone: +264 81 123 4567",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        uriHandler.openUri("mailto:support@swakopmundtourism.na")
                    }
                    .padding(vertical = 4.dp)
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email", modifier = Modifier.padding(end = 8.dp))
                Text(
                    "Email: support@swakopmundtourism.na",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Visual Separator
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))



            Text(
                "Frequently Asked Questions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            val faqs = remember {
                mutableStateListOf(
                    FAQItem("Are guided tours available?", "Yes, you can book guided tours in advance directly through the app. Look for the \"Tours\" section.", false),
                    FAQItem("How do I reset my password?", "You can reset your password from the login screen by tapping 'Forgot Password'. We'll send instructions to your registered email.", false),
                    FAQItem("What are the must-see attractions in Swakopmund?",
                        "Top attractions include the Jetty, Desert Explorers, the Moon Landscape, Welwitschia Drive, Swakopmund Museum, and the National Marine Aquarium.",
                        false),
                    FAQItem("Do I need a 4x4 vehicle to explore the area?",
                        "Some attractions like the Moon Landscape and Sandwich Harbour require a 4x4. You can find 4x4 rental options in the 'Transport' section.",
                        false),
                    FAQItem("Are airport transfers available?",
                        "Yes, you can book shuttle services from Walvis Bay Airport or Windhoek via the 'Transport' section of the app.",
                        false),
                    FAQItem("How do I get local emergency numbers?",
                        "Tap on 'Emergency Info' in the Help section to access police, ambulance, and other emergency contact numbers.",
                        false),





                    )
            }

            faqs.forEachIndexed { index, faqItem ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { faqs[index] = faqItem.copy(isExpanded = !faqItem.isExpanded) }
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(faqItem.question, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                        Icon(
                            imageVector = if (faqItem.isExpanded) Icons.Default.ChevronRight else Icons.Default.ChevronRight, 
                            contentDescription = if (faqItem.isExpanded) "Collapse" else "Expand"
                        )
                    }
                    if (faqItem.isExpanded) {
                        Text(
                            faqItem.answer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                if (index < faqs.size - 1) {
                    Divider(color = Color.LightGray, thickness = 0.5.dp) // Divider between FAQs
                }
            }


            // Visual Separator
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))


            // Helpful Topics / Quick Guides (NEW)
            Text(
                "Helpful Topics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            Text(
                "Tips for Your Swakopmund Visit:",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.bluebar),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text("• Bring sunscreen and water.")
            Text("• Wear comfortable walking shoes.")
            Text("• Check the weather forecast before your visit.")
            Text("• Recommended local etiquette (if any).")
            Text("• Emergency contact numbers (e.g., local police, ambulance).")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Using App Features:",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.bluebar),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text("• How to use the offline map.")
            Text("• Understanding booking confirmations.")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Troubleshooting Common Issues:",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.bluebar),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text("• App is slow or unresponsive? (Check internet, restart app, update app).")
            Text("• Location not working? (Check permissions, enable GPS).")


            // Visual Separator
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))



            Text(
                "App Version: 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

// Data class to hold FAQ item state
data class FAQItem(
    val question: String,
    val answer: String,
    val isExpanded: Boolean = false
)


@Preview(showBackground = true)
@Composable
fun SupportScreenPreview(){
    SupportScreen()
}
