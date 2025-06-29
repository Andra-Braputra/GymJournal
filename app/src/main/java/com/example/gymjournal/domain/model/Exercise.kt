package com.example.gymjournal.domain.model


data class Exercise(
    val id: Int = 0,
    val name: String = "",
    val muscle: String = "",
    val equipment: String = "",
    val description: String = ""
)
