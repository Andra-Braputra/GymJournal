package com.example.gymjournal.presentations.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.data.local.entity.ExerciseEntity
import com.example.gymjournal.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {
    var exercises by mutableStateOf<List<ExerciseEntity>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            repository.syncExercises()
            exercises = repository.getLocalExercises()
        }
    }
}