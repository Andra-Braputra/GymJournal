package com.example.gymjournal.presentations.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.domain.usecase.onboarding.SaveOnboardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val saveOnboardingStateUseCase: SaveOnboardingStateUseCase
) : ViewModel() {

    fun saveOnboardingState(completed: Boolean) {
        viewModelScope.launch {
            saveOnboardingStateUseCase(completed)
        }
    }
}