package com.example.gymjournal.data.repository

import com.example.gymjournal.data.api.WgerApiService
import com.example.gymjournal.data.local.dao.ExerciseDao
import com.example.gymjournal.data.local.entity.ExerciseEntity


class ExerciseRepository(
    private val api: WgerApiService,
    private val dao: ExerciseDao
) {
    suspend fun syncExercises() {
        val response = api.getExercises()
        val entities = response.results.map {
            ExerciseEntity(
                id = it.id,
                name = it.name,
                description = it.description
            )
        }
        dao.insertAll(entities)
    }

    suspend fun getLocalExercises(): List<ExerciseEntity> = dao.getAll()
}