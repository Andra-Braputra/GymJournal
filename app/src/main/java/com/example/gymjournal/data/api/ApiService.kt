package com.example.gymjournal.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("exercise/")
    suspend fun getExercises(
        @Query("language") languageId: Int = 2, // 2 = English
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): ExerciseResponse
}
