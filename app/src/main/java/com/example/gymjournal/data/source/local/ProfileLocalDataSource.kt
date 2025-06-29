package com.example.gymjournal.data.source.local

import com.example.gymjournal.data.local.dao.ProfileDao
import com.example.gymjournal.data.local.entity.ProfileEntity
import javax.inject.Inject

class ProfileLocalDataSource @Inject constructor(
    private val dao: ProfileDao
) {
    suspend fun getProfile(): ProfileEntity? = dao.getProfile()
    suspend fun saveProfile(profile: ProfileEntity) = dao.upsertProfile(profile)
}