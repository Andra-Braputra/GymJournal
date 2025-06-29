package com.example.gymjournal.domain.repository

interface OnboardingRepository {
    suspend fun saveOnboardingState(completed: Boolean)
    suspend fun isOnboardingCompleted(): Boolean
}