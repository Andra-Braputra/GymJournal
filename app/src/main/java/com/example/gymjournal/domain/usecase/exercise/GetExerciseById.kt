package com.example.gymjournal.domain.usecase.exercise

import com.example.gymjournal.domain.repository.ExerciseRepository

class GetExerciseById(private val repository: ExerciseRepository) {
    suspend operator fun invoke(id: Int) = repository.getExerciseById(id)
}