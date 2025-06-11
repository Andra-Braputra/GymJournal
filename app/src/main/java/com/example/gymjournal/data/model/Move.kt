package com.example.gymjournal.data.model

data class Move(
    val id: Int,
    val name: String,
    val description: String,
    val category: String, // e.g. "Chest", "Legs", "Back"
    val videoUrl: String? = null // optional
)
