package com.example.gymjournal.domain.usecase.onboarding

import com.example.gymjournal.domain.repository.OnboardingRepository
import javax.inject.Inject

class SaveOnboardingStateUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveOnboardingState(completed)
    }
}