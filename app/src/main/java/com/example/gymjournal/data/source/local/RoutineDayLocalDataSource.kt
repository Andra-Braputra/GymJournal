package com.example.gymjournal.data.source.local

import com.example.gymjournal.data.local.entity.RoutineDayEntity
import kotlinx.coroutines.flow.Flow

interface RoutineDayLocalDataSource {
    suspend fun insertRoutineDay(day: RoutineDayEntity)
    suspend fun insertRoutineDays(days: List<RoutineDayEntity>)
    suspend fun updateRoutineDay(day: RoutineDayEntity)
    suspend fun deleteRoutineDay(day: RoutineDayEntity)
    suspend fun deleteRoutineDaysByRoutine(routineId: Int)
    fun getRoutineDaysByRoutineId(routineId: Int): Flow<List<RoutineDayEntity>>
}