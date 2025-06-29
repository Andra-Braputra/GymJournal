package com.example.gymjournal.presentations.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.data.mapper.toDomain
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.domain.model.Profile
import com.example.gymjournal.domain.usecase.user.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases
) : ViewModel() {

    private val _profileState = MutableStateFlow(Profile())
    val profileState: StateFlow<Profile> = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val profileEntity = profileUseCases.getProfile()
            val profile = profileEntity?.toDomain() ?: Profile()
            _profileState.value = profile
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            profileUseCases.saveProfile(profile.toEntity())
            _profileState.value = profile
        }
    }
}