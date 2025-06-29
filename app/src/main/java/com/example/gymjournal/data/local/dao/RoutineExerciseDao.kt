package com.example.gymjournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gymjournal.data.local.entity.RoutineExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: RoutineExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<RoutineExerciseEntity>)

    @Update
    suspend fun updateExercise(exercise: RoutineExerciseEntity)

    @Query("SELECT * FROM routine_exercises WHERE dayId = :dayId")
    fun getExercisesByDay(dayId: Int): Flow<List<RoutineExerciseEntity>>

    @Query("SELECT * FROM routine_exercises WHERE id = :id")
    suspend fun getExerciseById(id: Int): RoutineExerciseEntity?

    @Delete
    suspend fun deleteExercise(exercise: RoutineExerciseEntity)

    @Query("DELETE FROM routine_exercises WHERE dayId = :dayId")
    suspend fun deleteExercisesByDay(dayId: Int)
}

