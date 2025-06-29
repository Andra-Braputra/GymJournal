package com.example.gymjournal.domain.repository.routine

import com.example.gymjournal.domain.model.RoutineExercise
import kotlinx.coroutines.flow.Flow


interface RoutineExerciseRepository {
    suspend fun insertRoutineExercise(exercise: RoutineExercise)
    suspend fun insertRoutineExercises(exercises: List<RoutineExercise>)
    suspend fun updateRoutineExercise(exercise: RoutineExercise)
    suspend fun deleteRoutineExercise(exercise: RoutineExercise)
    suspend fun deleteRoutineExercisesByDay(dayId: Int)
    fun getRoutineExercisesByDay(dayId: Int): Flow<List<RoutineExercise>>
    suspend fun getRoutineExerciseById(id: Int): RoutineExercise?
}