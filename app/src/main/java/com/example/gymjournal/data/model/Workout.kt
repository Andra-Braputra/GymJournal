package com.example.gymjournal.data.model

data class Workout(
    val id: Int,
    val userId: Int,
    val routineId: Int,
    val date: String,
    val durationMinutes: Int,
    val setsCompleted: Int,
    val notes: String? = null
)
