package com.example.gymjournal.domain.usecase.auth

import com.example.gymjournal.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(): Boolean {
        return authenticationRepository.isLoggedIn()
    }
}