package com.example.swakopmundapp.database.localRepoInt

import com.example.swakopmundapp.core.dto.SignUpResponseDto
import com.example.swakopmundapp.database.databaseView.ProfileView
import kotlinx.coroutines.flow.Flow

interface LocalProfileRepoInt {

    suspend fun insertProfile(profileDto: SignUpResponseDto)

    suspend fun getProfile(): Flow<ProfileView>

    fun getUser(): ProfileView?

    fun getUserEmail(): String

    fun getUserFirstName(): String

    fun getUserLastName(): String

    fun getHomeAddress(): String

    suspend fun updateProfile(profileView: ProfileView)

    fun clearDatabase()

}