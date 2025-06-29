package com.example.gymjournal.domain.usecase.onboarding

data class OnboardingUseCases(
    val isCompleted: suspend () -> Boolean,
    val setCompleted: suspend () -> Unit
)