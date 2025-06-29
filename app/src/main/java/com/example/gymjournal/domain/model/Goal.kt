package com.example.gymjournal.domain.model

data class Goal(
    val id: Int = 0,
    val name: String,
    val detail: String,
    val deadline: String
)