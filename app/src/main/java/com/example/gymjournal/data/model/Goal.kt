package com.example.gymjournal.data.model

data class Goal(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val targetDate: String
)
