package com.example.gymjournal.data.source.local

import com.example.gymjournal.data.local.entity.RoutineEntity
import kotlinx.coroutines.flow.Flow

interface RoutineLocalDataSource {
    suspend fun insertRoutine(routine: RoutineEntity): Long
    suspend fun updateRoutine(routine: RoutineEntity)
    suspend fun deleteRoutine(routine: RoutineEntity)
    fun getAllRoutines(): Flow<List<RoutineEntity>>
    fun getSelectedRoutine(): Flow<RoutineEntity?>
    suspend fun deselectAllRoutines()
}
