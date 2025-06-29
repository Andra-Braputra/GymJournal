package com.example.gymjournal.domain.usecase.theme

import com.example.gymjournal.domain.repository.ThemeRepository
import javax.inject.Inject

class ToggleThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke() = repository.toggleTheme()
}