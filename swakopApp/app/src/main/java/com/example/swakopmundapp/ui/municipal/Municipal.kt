package com.example.swakopmundapp.ui.municipal

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swakopmundapp.R



@Composable
fun MunicipalScreen(
    onBack: () -> Unit,
    onOptionSelected: (MunicipalOption) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize()) {

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
                        .padding(start = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Municipal",
                    color = Color.White,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }


            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listOf(
                    MunicipalOption.BankDetails,
                    MunicipalOption.Departments,
                    MunicipalOption.Procurement,
                    MunicipalOption.Downloads,
                    MunicipalOption.ReportAnIssue
                )) { option ->
                    MunicipalRowItem(option, onOptionSelected)
                    HorizontalDivider(
                        modifier = Modifier.padding(start = 72.dp, end = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

sealed class MunicipalOption(
    @DrawableRes val icon: Int,
    val title: String
) {
    object BankDetails   : MunicipalOption(R.drawable.bank,   "Bank Details")
    object Departments   : MunicipalOption(R.drawable.departments,    "Departments")
    object Procurement   : MunicipalOption(R.drawable.procurement,    "Procurement")
    object Downloads     : MunicipalOption(R.drawable.downloads,      "Downloads")
    object ReportAnIssue : MunicipalOption(R.drawable.reportissue,  "Report an Issue")
}

@Composable
private fun MunicipalRowItem(
    option: MunicipalOption,
    onOptionSelected: (MunicipalOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOptionSelected(option) }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = option.icon),
            contentDescription = option.title,
            tint = Color.Unspecified,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = option.title,
            color = colorResource(id = R.color.bluebar),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.Gray.copy(alpha = 0.6f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MunicipalScreenPreview() {
    MunicipalScreen(
        onBack = {},
        onOptionSelected = {}
    )
}
