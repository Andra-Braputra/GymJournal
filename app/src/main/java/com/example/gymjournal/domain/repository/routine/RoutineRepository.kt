package com.example.gymjournal.domain.repository.routine

import com.example.gymjournal.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun insertRoutine(routine: Routine): Long
    suspend fun updateRoutine(routine: Routine)
    suspend fun deleteRoutine(routine: Routine)
    fun getAllRoutines(): Flow<List<Routine>>
    fun getSelectedRoutine(): Flow<Routine?>
    suspend fun deselectAllRoutines()
}