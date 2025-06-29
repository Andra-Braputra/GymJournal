package com.example.gymjournal.data.source.local.impl

import com.example.gymjournal.data.local.dao.RoutineExerciseDao
import com.example.gymjournal.data.local.entity.RoutineExerciseEntity
import com.example.gymjournal.data.source.local.RoutineExerciseLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineExerciseLocalDataSourceImpl @Inject constructor(
    private val dao: RoutineExerciseDao
) : RoutineExerciseLocalDataSource {

    override suspend fun insertRoutineExercise(exercise: RoutineExerciseEntity) {
        dao.insertExercise(exercise)
    }

    override suspend fun insertRoutineExercises(exercises: List<RoutineExerciseEntity>) {
        dao.insertExercises(exercises)
    }

    override suspend fun updateRoutineExercise(exercise: RoutineExerciseEntity) {
        dao.updateExercise(exercise)
    }

    override suspend fun deleteRoutineExercise(exercise: RoutineExerciseEntity) {
        dao.deleteExercise(exercise)
    }

    override suspend fun deleteRoutineExercisesByDay(dayId: Int) {
        dao.deleteExercisesByDay(dayId)
    }

    override fun getRoutineExercisesByDay(dayId: Int): Flow<List<RoutineExerciseEntity>> {
        return dao.getExercisesByDay(dayId)
    }

    override suspend fun getRoutineExerciseById(id: Int): RoutineExerciseEntity? {
        return dao.getExerciseById(id)
    }
}