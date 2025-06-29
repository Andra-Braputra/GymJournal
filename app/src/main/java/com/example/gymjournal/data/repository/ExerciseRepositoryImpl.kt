package com.example.gymjournal.data.repository

import com.example.gymjournal.data.local.dao.ExerciseDao
import com.example.gymjournal.data.mapper.toDomain
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.data.source.remote.FirestoreExerciseSource
import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val firestoreSource: FirestoreExerciseSource,
    private val dao: ExerciseDao
) : ExerciseRepository {

    override fun getLocalExercises(): Flow<List<Exercise>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncExercisesFromRemote() {
        val remoteExercises = firestoreSource.getAllExercises() // List<ExerciseDto>
        val localExercises = dao.getAllExercisesOnce()

        val toInsert = remoteExercises
            .map { it.toDomain() }
            .filterNot { remote ->
                localExercises.any { it.id == remote.id }
            }

        dao.insertAll(toInsert.map { it.toEntity() })
    }

    override suspend fun getExerciseById(id: Int): Exercise? {
        return dao.getById(id)?.toDomain()
    }

    override suspend fun insertExercise(exercise: Exercise) {
        dao.insert(exercise.toEntity())
    }

    override suspend fun updateExercise(exercise: Exercise) {
        dao.update(exercise.toEntity())
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        dao.delete(exercise.toEntity())
    }
}
