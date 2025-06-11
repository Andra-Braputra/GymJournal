package com.example.gymjournal.data.model

data class Routine(
    val id: Int,
    val userId: Int,
    val title: String,
    val description: String,
    val moves: List<Move> = emptyList()
)
