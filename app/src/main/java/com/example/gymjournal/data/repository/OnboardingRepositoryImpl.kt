package com.example.gymjournal.data.repository

import com.example.gymjournal.data.preference.PreferencesManager
import com.example.gymjournal.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : OnboardingRepository {

    override suspend fun saveOnboardingState(completed: Boolean) {
        if (completed) preferencesManager.setOnboardingComplete()
    }

    override suspend fun isOnboardingCompleted(): Boolean {
        return preferencesManager.isOnboardingComplete.first()
    }
}

