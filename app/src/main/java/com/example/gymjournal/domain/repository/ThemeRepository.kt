package com.example.gymjournal.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun isDarkTheme(): Flow<Boolean>
    suspend fun toggleTheme()
}