package com.example.gymjournal.domain.repository

import com.example.gymjournal.data.local.entity.ProfileEntity

interface ProfileRepository {
    suspend fun getProfile(): ProfileEntity?
    suspend fun saveProfile(profile: ProfileEntity)
}