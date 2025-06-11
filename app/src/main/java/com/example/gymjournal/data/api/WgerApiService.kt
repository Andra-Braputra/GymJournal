package com.example.gymjournal.data.api

import retrofit2.http.GET

interface WgerApiService {
    @GET("exercise/?language=2")
    suspend fun getExercises(): ExerciseResponse
}
