package com.example.swakopmundapp.data.model.FavouriteMemories

import android.net.Uri
import java.util.UUID

data class FavouriteMemory(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val imageUri: Uri,
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
)