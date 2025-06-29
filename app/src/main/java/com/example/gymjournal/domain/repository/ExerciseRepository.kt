package com.example.gymjournal.domain.repository

import com.example.gymjournal.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getLocalExercises(): Flow<List<Exercise>>

    suspend fun syncExercisesFromRemote()

    suspend fun getExerciseById(id: Int): Exercise?

    suspend fun insertExercise(exercise: Exercise)

    suspend fun updateExercise(exercise: Exercise)

    suspend fun deleteExercise(exercise: Exercise)
}