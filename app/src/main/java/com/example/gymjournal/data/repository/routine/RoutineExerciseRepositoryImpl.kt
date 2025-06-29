package com.example.gymjournal.data.repository.routine

import com.example.gymjournal.data.mapper.toDomain
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.data.source.local.RoutineExerciseLocalDataSource
import com.example.gymjournal.domain.model.RoutineExercise
import com.example.gymjournal.domain.repository.routine.RoutineExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineExerciseRepositoryImpl @Inject constructor(
    private val localDataSource: RoutineExerciseLocalDataSource
) : RoutineExerciseRepository {

    override suspend fun insertRoutineExercise(exercise: RoutineExercise) {
        localDataSource.insertRoutineExercise(exercise.toEntity())
    }

    override suspend fun insertRoutineExercises(exercises: List<RoutineExercise>) {
        localDataSource.insertRoutineExercises(exercises.map { it.toEntity() })
    }

    override suspend fun updateRoutineExercise(exercise: RoutineExercise) {
        localDataSource.updateRoutineExercise(exercise.toEntity())
    }

    override suspend fun deleteRoutineExercise(exercise: RoutineExercise) {
        localDataSource.deleteRoutineExercise(exercise.toEntity())
    }

    override suspend fun deleteRoutineExercisesByDay(dayId: Int) {
        localDataSource.deleteRoutineExercisesByDay(dayId)
    }

    override fun getRoutineExercisesByDay(dayId: Int): Flow<List<RoutineExercise>> {
        return localDataSource.getRoutineExercisesByDay(dayId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getRoutineExerciseById(id: Int): RoutineExercise? {
        return localDataSource.getRoutineExerciseById(id)?.toDomain()
    }
}