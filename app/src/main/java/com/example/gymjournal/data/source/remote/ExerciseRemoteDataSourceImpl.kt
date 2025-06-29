package com.example.gymjournal.data.source.remote

import com.example.gymjournal.data.dto.ExerciseDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExerciseRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ExerciseRemoteDataSource {

    private val collection = firestore.collection("exercises")

    override suspend fun fetchAll(): List<ExerciseDto> {
        return try {
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { it.toObject(ExerciseDto::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}