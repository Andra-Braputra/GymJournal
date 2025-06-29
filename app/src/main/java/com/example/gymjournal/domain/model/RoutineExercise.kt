package com.example.gymjournal.domain.model

data class RoutineExercise(
    val id: Int = 0,
    val dayId: Int,
    val exerciseId: Int,
    val exerciseName: String,
    val sets: Int,
    val reps: Int,
    val weight: Float
)