package com.example.swakopmundapp.data.model.FavouriteMemories

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class FavouriteMemoriesViewModel : ViewModel() {
    val memories = mutableStateListOf<FavouriteMemory>()

    fun addMemory(username: String, imageUri: Uri, description: String) {
        memories.add(FavouriteMemory(username = username, imageUri = imageUri, description = description))
    }
}