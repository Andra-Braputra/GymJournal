package com.example.gymjournal.domain.model

data class RoutineDay(
    val id: Int = 0,
    val routineId: Int,
    val name: String,         // Tambahkan ini
    val order: Int,           // Tambahkan ini
    val isRestDay: Boolean = true
)