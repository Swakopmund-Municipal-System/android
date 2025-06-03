package com.example.swakopmundapp.database.localRepoImp

import com.example.swakopmundapp.core.dto.SignUpResponseDto
import com.example.swakopmundapp.database.dao.ProfileDao
import com.example.swakopmundapp.database.databaseView.ProfileView
import com.example.swakopmundapp.database.localRepoInt.LocalProfileRepoInt
import kotlinx.coroutines.flow.Flow

internal class LocalProfileRepoImp(
    private val profileDao: ProfileDao
) : LocalProfileRepoInt {

    override suspend fun insertProfile(profileDto: SignUpResponseDto) = profileDao.insertUserProfile(profileDto)

    override suspend fun getProfile(): Flow<ProfileView> = profileDao.getProfile()

    override fun getUser(): ProfileView? = profileDao.getUser()

    override fun getUserEmail(): String = profileDao.getUserEmail()

    override fun getUserFirstName(): String = profileDao.getUserFirstName()

    override fun getUserLastName(): String = profileDao.getUserLastName()

    override fun getHomeAddress(): String = profileDao.getHomeAddress()

    override suspend fun updateProfile(profileView: ProfileView) = profileDao.updateProfile(profileView)

    override fun clearDatabase() = profileDao.clearDatabase()

}