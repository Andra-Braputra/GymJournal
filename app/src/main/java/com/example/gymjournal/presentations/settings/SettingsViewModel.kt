package com.example.gymjournal.presentations.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.domain.usecase.auth.ChangePasswordUseCase
import com.example.gymjournal.domain.usecase.auth.LogoutUseCase
import com.example.gymjournal.domain.usecase.theme.GetThemeUseCase
import com.example.gymjournal.domain.usecase.theme.ToggleThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val toggleThemeUseCase: ToggleThemeUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = getThemeUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun toggleTheme() {
        viewModelScope.launch {
            toggleThemeUseCase()
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase()
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = changePasswordUseCase(currentPassword, newPassword)
            result.fold(
                onSuccess = { onSuccess() },
                onFailure = { onError(it.localizedMessage ?: "Failed to change password") }
            )
        }
    }
}
