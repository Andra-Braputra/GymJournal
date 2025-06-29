package com.example.gymjournal.data.api

import com.example.gymjournal.data.dto.ExerciseDto

data class ExerciseResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ExerciseDto>
)