package com.example.gymjournal.data.repository

import com.example.gymjournal.data.local.entity.ProfileEntity
import com.example.gymjournal.data.source.local.ProfileLocalDataSource
import com.example.gymjournal.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val localDataSource: ProfileLocalDataSource
) : ProfileRepository {
    override suspend fun getProfile() = localDataSource.getProfile()
    override suspend fun saveProfile(profile: ProfileEntity) = localDataSource.saveProfile(profile)
}