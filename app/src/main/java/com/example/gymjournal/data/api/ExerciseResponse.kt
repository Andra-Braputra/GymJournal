package com.example.gymjournal.data.api

data class ExerciseResponse(
    val results: List<ApiExercise>
)

data class ApiExercise(
    val id: Int,
    val name: String,
    val description: String
)