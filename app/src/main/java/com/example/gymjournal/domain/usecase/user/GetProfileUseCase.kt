package com.example.gymjournal.domain.usecase.user

import com.example.gymjournal.domain.repository.ProfileRepository
import javax.inject.Inject


class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() = repository.getProfile()
}
