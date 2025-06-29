package com.example.gymjournal.domain.usecase.theme

import com.example.gymjournal.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isDarkTheme()
}