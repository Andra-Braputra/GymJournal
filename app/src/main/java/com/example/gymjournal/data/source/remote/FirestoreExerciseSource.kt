package com.example.gymjournal.data.source.remote

import com.example.gymjournal.data.dto.ExerciseDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreExerciseSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getAllExercises(): List<ExerciseDto> {
        val snapshot = firestore.collection("exercises").get().await()
        return snapshot.documents.mapNotNull {
            it.toObject(ExerciseDto::class.java)
        }
    }
}