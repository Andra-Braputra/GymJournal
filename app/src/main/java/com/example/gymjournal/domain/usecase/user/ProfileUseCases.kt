package com.example.gymjournal.domain.usecase.user

data class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val saveProfile: SaveProfileUseCase
)