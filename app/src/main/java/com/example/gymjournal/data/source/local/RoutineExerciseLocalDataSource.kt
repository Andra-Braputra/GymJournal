package com.example.gymjournal.data.source.local

import com.example.gymjournal.data.local.entity.RoutineExerciseEntity
import kotlinx.coroutines.flow.Flow

interface RoutineExerciseLocalDataSource {
    suspend fun insertRoutineExercise(exercise: RoutineExerciseEntity)
    suspend fun insertRoutineExercises(exercises: List<RoutineExerciseEntity>)
    suspend fun updateRoutineExercise(exercise: RoutineExerciseEntity)
    suspend fun deleteRoutineExercise(exercise: RoutineExerciseEntity)
    suspend fun deleteRoutineExercisesByDay(dayId: Int)
    fun getRoutineExercisesByDay(dayId: Int): Flow<List<RoutineExerciseEntity>>
    suspend fun getRoutineExerciseById(id: Int): RoutineExerciseEntity?
}