package com.example.gymjournal.presentations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.domain.usecase.exercise.ExerciseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ExerciseUiState {
    object Loading : ExerciseUiState()
    data class Success(val exercises: List<Exercise>) : ExerciseUiState()
    data class Error(val message: String) : ExerciseUiState()
}

sealed class ExerciseFormEvent {
    data class ShowSnackbar(val message: String) : ExerciseFormEvent()
}

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val useCases: ExerciseUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)
    val uiState: StateFlow<ExerciseUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<ExerciseFormEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        syncAndLoadExercises()
    }

    fun syncAndLoadExercises() {
        viewModelScope.launch {
            _uiState.value = ExerciseUiState.Loading
            try {
                useCases.syncExercises()
                useCases.getExercises().collectLatest { exercises: List<Exercise> ->
                    _uiState.value = ExerciseUiState.Success(exercises)
                }
            } catch (e: Exception) {
                _uiState.value = ExerciseUiState.Error("Failed to load exercises")
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Failed to sync exercises"))
            }
        }
    }

    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                useCases.insertExercise(exercise)
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Exercise added"))
            } catch (e: Exception) {
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Failed to add exercise"))
            }
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                useCases.updateExercise(exercise)
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Exercise updated"))
            } catch (e: Exception) {
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Failed to update exercise"))
            }
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                useCases.deleteExercise(exercise)
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Exercise deleted"))
            } catch (e: Exception) {
                _eventFlow.emit(ExerciseFormEvent.ShowSnackbar("Failed to delete exercise"))
            }
        }
    }

    suspend fun getExerciseById(id: Int): Exercise? = useCases.getExerciseById(id)
}