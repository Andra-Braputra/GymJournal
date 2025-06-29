package com.example.gymjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val ONBOARDING_KEY = booleanPreferencesKey("onboarding_complete")
    }

    suspend fun setOnboardingComplete() {
        dataStore.edit { it[ONBOARDING_KEY] = true }
    }

    val isOnboardingComplete: Flow<Boolean> = dataStore.data
        .map { prefs -> prefs[ONBOARDING_KEY] ?: false }
}