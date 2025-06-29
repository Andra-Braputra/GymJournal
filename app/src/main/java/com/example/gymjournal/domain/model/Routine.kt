package com.example.gymjournal.domain.model

data class Routine(
    val id: Int = 0,
    val name: String,
    val isSelected: Boolean = false
)