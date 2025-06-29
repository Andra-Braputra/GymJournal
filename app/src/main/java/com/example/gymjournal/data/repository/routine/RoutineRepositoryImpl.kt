package com.example.gymjournal.data.repository.routine

import com.example.gymjournal.data.mapper.toDomain
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.data.source.local.RoutineLocalDataSource
import com.example.gymjournal.domain.model.Routine
import com.example.gymjournal.domain.repository.routine.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val localDataSource: RoutineLocalDataSource
) : RoutineRepository {

    override suspend fun insertRoutine(routine: Routine): Long {
        return localDataSource.insertRoutine(routine.toEntity())
    }


    override suspend fun updateRoutine(routine: Routine) {
        localDataSource.updateRoutine(routine.toEntity())
    }

    override suspend fun deleteRoutine(routine: Routine) {
        localDataSource.deleteRoutine(routine.toEntity())
    }

    override fun getAllRoutines(): Flow<List<Routine>> {
        return localDataSource.getAllRoutines().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getSelectedRoutine(): Flow<Routine?> {
        return localDataSource.getSelectedRoutine().map { it?.toDomain() }
    }

    override suspend fun deselectAllRoutines() {
        localDataSource.deselectAllRoutines()
    }
}
