package com.example.gymjournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gymjournal.data.local.entity.RoutineDayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: RoutineDayEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<RoutineDayEntity>)

    @Update
    suspend fun updateDay(day: RoutineDayEntity)

    @Query("SELECT * FROM routine_days WHERE routineId = :routineId ORDER BY id ASC")
    fun getDaysForRoutine(routineId: Int): Flow<List<RoutineDayEntity>>

    @Delete
    suspend fun deleteDay(day: RoutineDayEntity)

    @Query("DELETE FROM routine_days WHERE routineId = :routineId")
    suspend fun deleteDaysByRoutine(routineId: Int)
}

