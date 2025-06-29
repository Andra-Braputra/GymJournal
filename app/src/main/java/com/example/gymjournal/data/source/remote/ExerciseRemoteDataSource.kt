package com.example.gymjournal.data.source.remote

import com.example.gymjournal.data.dto.ExerciseDto

interface ExerciseRemoteDataSource {
    suspend fun fetchAll(): List<ExerciseDto>
}