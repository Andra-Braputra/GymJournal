package com.example.gymjournal.presentations.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymjournal.presentations.settings.SettingsViewModel
import com.example.gymjournal.ui.theme.AppTheme

@Composable
fun GymJournalThemeWrapper(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

    AppTheme(darkTheme = isDarkTheme) {
        content()
    }
}