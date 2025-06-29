package com.example.gymjournal.domain.usecase.user

import com.example.gymjournal.data.local.entity.ProfileEntity
import com.example.gymjournal.domain.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: ProfileEntity) = repository.saveProfile(profile)
}