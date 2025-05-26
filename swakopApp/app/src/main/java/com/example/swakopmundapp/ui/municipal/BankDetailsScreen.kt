package com.example.swakopmundapp.ui.municipal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.swakopmundapp.data.model.municipal.BankDetailsViewModel

@Composable
fun BankDetailsScreen(viewModel: BankDetailsViewModel = BankDetailsViewModel()) {
    val bank = viewModel.bankDetails
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Municipal Bank Details", style = MaterialTheme.typography.headlineSmall)

        BankDetailItem(label = "Bank Name", value = bank.bankName)
        BankDetailItem(label = "Account Number", value = bank.accountNumber, clipboardManager)
        BankDetailItem(label = "Branch Code", value = bank.branchCode, clipboardManager)
        BankDetailItem(label = "SWIFT Code", value = bank.swiftCode, clipboardManager)
    }
}

@Composable
fun BankDetailItem(label: String, value: String, clipboardManager: ClipboardManager? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.labelSmall)
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
        if (clipboardManager != null) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString(value))
                    }
            )
        }
    }
}
