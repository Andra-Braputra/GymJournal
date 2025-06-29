package com.example.gymjournal.data.repository

import com.example.gymjournal.data.preference.ThemePreferenceManager
import com.example.gymjournal.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val preferenceManager: ThemePreferenceManager
) : ThemeRepository {
    override fun isDarkTheme(): Flow<Boolean> = preferenceManager.isDarkTheme
    override suspend fun toggleTheme() = preferenceManager.toggleTheme()
}
