package com.example.gymjournal.presentations.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.domain.usecase.auth.IsLoggedInUseCase
import com.example.gymjournal.domain.usecase.onboarding.GetOnboardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnboardingStateUseCase: GetOnboardingStateUseCase,
    private val isUserLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                val isOnboardingDone = getOnboardingStateUseCase()
                val isLoggedIn = isUserLoggedInUseCase()

                Timber.tag("Splash").d("Onboarding: $isOnboardingDone | LoggedIn: $isLoggedIn")

                _uiState.value = SplashUiState(
                    isLoading = false,
                    isOnboardingCompleted = isOnboardingDone,
                    isLoggedIn = isLoggedIn
                )
            } catch (e: Exception) {
                Timber.e(e, "Splash init error")
                _uiState.value = SplashUiState(
                    isLoading = false,
                    isOnboardingCompleted = false,
                    isLoggedIn = false
                )
            }
        }
    }
}

data class SplashUiState(
    val isLoading: Boolean = true,
    val isOnboardingCompleted: Boolean = false,
    val isLoggedIn: Boolean = false
)
