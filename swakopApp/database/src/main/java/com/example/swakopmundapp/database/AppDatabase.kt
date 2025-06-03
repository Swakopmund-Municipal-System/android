package com.example.swakopmundapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.swakopmundapp.database.dao.ProfileDao
import com.example.swakopmundapp.database.entity.ProfileEntity
import com.example.swakopmundapp.database.util.Converters

@Database(
    entities = [
        ProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    internal abstract fun profileDao(): ProfileDao

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    companion object {

        // For singleton instantiation
        @Volatile
        internal var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "SwakopmundApp")
                .addCallback(object : Callback() {})
                .allowMainThreadQueries()
                .addMigrations()
                .build()
        }
    }

}