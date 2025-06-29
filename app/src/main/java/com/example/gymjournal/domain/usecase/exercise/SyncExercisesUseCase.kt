package com.example.gymjournal.domain.usecase.exercise

import com.example.gymjournal.domain.repository.ExerciseRepository

class SyncExercisesUseCase(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke() = repository.syncExercisesFromRemote()
}