package com.example.swakopmundapp.data.model.municipal

import androidx.lifecycle.ViewModel

class BankDetailsViewModel : ViewModel() {
    val bankDetails = BankDetail(
        bankName = "Bank Windhoek - Swakopmund Branch",
        accountNumber = "123 456 7890",
        branchCode = "481972",
        swiftCode = "BWLINANX"
    )
}