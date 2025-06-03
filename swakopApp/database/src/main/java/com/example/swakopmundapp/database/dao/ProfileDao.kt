package com.example.swakopmundapp.database.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.swakopmundapp.core.dto.SignUpResponseDto
import com.example.swakopmundapp.database.AppDatabase
import com.example.swakopmundapp.database.databaseView.ProfileView
import com.example.swakopmundapp.database.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ProfileDao {

    /**
     * Inserts
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProfile(profile: ProfileEntity)

    fun insertUserProfile(profile: SignUpResponseDto) {
        try {
            insertProfile(
                ProfileEntity(
                    Id = profile.email,
                    Email = profile.email,
                    FirstName = profile.firstName,
                    LastName = profile.lastName,
                    HomeAddress = profile.homeAddress
                )
            )
        } catch (e: Exception) {
            Log.e("DB_PROFILE_INSERT", "error -> $e")
        }
    }

    /**
     * Get Views
     */
    @Query("SELECT * FROM ProfileEntity LIMIT 1")
    fun getProfile(): Flow<ProfileView>

    @Query("SELECT * FROM ProfileEntity LIMIT 1")
    fun getUser(): ProfileView?

    /**
     * Get
     */
    @Query("SELECT * FROM ProfileEntity LIMIT 1")
    fun getProfileSnapshot(): ProfileEntity?

    @Query("SELECT Email FROM ProfileEntity")
    fun getUserEmail(): String

    @Query("SELECT FirstName FROM ProfileEntity")
    fun getUserFirstName(): String

    @Query("SELECT LastName FROM ProfileEntity")
    fun getUserLastName(): String

    @Query("SELECT HomeAddress FROM ProfileEntity")
    fun getHomeAddress(): String

    /**
     * Update
     */
    @Update
    fun update(profile: ProfileEntity)

    fun updateProfile(profileView: ProfileView) {

        getProfileSnapshot()?.let { profile ->
            profile.FirstName = profileView.FirstName
            profile.LastName = profileView.LastName
            profile.Email = profileView.Email
            profile.HomeAddress = profileView.HomeAddress

            try {
                update(profile)
            } catch (e: Exception) {
                Log.e("DB_PROFILE_UPDATE", "error -> $e")
            }
        } ?: kotlin.run {
            print("Not found")
        }
    }


    /**
     * Delete
     */
    @Transaction
    fun clearDatabase() {
        try {
            AppDatabase.instance?.clearAllTables()
        } catch (e: Exception) {
            Log.e("DB_PROFILE_DELETE", "error -> $e")
        }
    }

}