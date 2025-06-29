package com.example.gymjournal.domain.repository.routine

import com.example.gymjournal.domain.model.RoutineDay
import kotlinx.coroutines.flow.Flow

interface RoutineDayRepository {
    suspend fun insertDay(day: RoutineDay)
    suspend fun insertDays(days: List<RoutineDay>)
    suspend fun updateDay(day: RoutineDay)
    suspend fun deleteDay(day: RoutineDay)
    suspend fun deleteDaysByRoutine(routineId: Int)
    fun getDaysForRoutine(routineId: Int): Flow<List<RoutineDay>>
}