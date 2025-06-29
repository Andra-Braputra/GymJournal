package com.example.gymjournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gymjournal.data.local.entity.RoutineEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RoutineDao {

    @Insert
    suspend fun insertRoutine(routine: RoutineEntity): Long

    @Update
    suspend fun updateRoutine(routine: RoutineEntity)

    @Delete
    suspend fun deleteRoutine(routine: RoutineEntity)

    @Query("SELECT * FROM routines ORDER BY name ASC")
    fun getAllRoutines(): Flow<List<RoutineEntity>>

    @Query("SELECT * FROM routines WHERE isSelected = 1 LIMIT 1")
    fun getSelectedRoutine(): Flow<RoutineEntity?>

    @Query("UPDATE routines SET isSelected = 0")
    suspend fun deselectAllRoutines()
}

