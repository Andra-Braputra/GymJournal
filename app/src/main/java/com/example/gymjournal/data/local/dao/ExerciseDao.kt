package com.example.gymjournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gymjournal.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises")
    suspend fun getAllExercisesOnce(): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getById(id: Int): ExerciseEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE) // ⚠️ Penting untuk cegah duplikat!
    suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: ExerciseEntity)

    @Update
    suspend fun update(exercise: ExerciseEntity)

    @Delete
    suspend fun delete(exercise: ExerciseEntity)
}