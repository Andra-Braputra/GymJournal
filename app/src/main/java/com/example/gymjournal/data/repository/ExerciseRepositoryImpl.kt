package com.example.gymjournal.data.repository

import com.example.gymjournal.data.local.dao.ExerciseDao
import com.example.gymjournal.data.mapper.toDomain
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.data.source.remote.ExerciseRemoteDataSource
import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val remoteDataSource: ExerciseRemoteDataSource,
    private val exerciseDao: ExerciseDao
) : ExerciseRepository {

    override fun getLocalExercises(): Flow<List<Exercise>> {
        return exerciseDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncExercisesFromRemote() {
        try {
            val remoteData = remoteDataSource.fetchAll()
            val entities = remoteData.map { it.toDomain().toEntity() }

            exerciseDao.clearAll()
            exerciseDao.insertAll(entities)
        } catch (e: Exception) {
            e.printStackTrace()
            // You could log error or rethrow if needed
        }
    }

    override suspend fun getExerciseById(id: Int): Exercise? {
        return exerciseDao.getById(id)?.toDomain()
    }

    override suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insert(exercise.toEntity())
    }

    override suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise.toEntity())
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.delete(exercise.toEntity())
    }
}