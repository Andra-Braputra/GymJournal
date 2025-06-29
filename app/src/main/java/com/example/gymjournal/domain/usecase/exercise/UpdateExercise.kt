package com.example.gymjournal.domain.usecase.exercise

import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.domain.repository.ExerciseRepository

class UpdateExercise(private val repository: ExerciseRepository) {
    suspend operator fun invoke(exercise: Exercise) = repository.updateExercise(exercise)
}