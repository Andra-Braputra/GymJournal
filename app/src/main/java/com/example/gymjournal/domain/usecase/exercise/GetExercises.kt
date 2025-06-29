package com.example.gymjournal.domain.usecase.exercise

import com.example.gymjournal.domain.repository.ExerciseRepository

class GetExercises(private val repository: ExerciseRepository) {
    operator fun invoke() = repository.getLocalExercises()
}