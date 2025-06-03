package com.example.swakopmundapp.database.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {

    // Date
    @TypeConverter
    fun dateFromString(value: String?): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        value?.let {
            return sdf.parse(it)
        } ?: run {
            return null
        }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        date?.let {
            return sdf.format(date)
        } ?: run {
            return null
        }
    }

}