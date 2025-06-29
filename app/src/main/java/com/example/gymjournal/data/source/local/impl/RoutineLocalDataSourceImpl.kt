package com.example.gymjournal.data.source.local.impl

import com.example.gymjournal.data.local.dao.RoutineDao
import com.example.gymjournal.data.local.entity.RoutineEntity
import com.example.gymjournal.data.source.local.RoutineLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineLocalDataSourceImpl @Inject constructor(
    private val dao: RoutineDao
) : RoutineLocalDataSource {

    override suspend fun insertRoutine(routine: RoutineEntity): Long {
        return dao.insertRoutine(routine)
    }

    override suspend fun updateRoutine(routine: RoutineEntity) {
        dao.updateRoutine(routine)
    }

    override suspend fun deleteRoutine(routine: RoutineEntity) {
        dao.deleteRoutine(routine)
    }

    override fun getAllRoutines(): Flow<List<RoutineEntity>> {
        return dao.getAllRoutines()
    }

    override fun getSelectedRoutine(): Flow<RoutineEntity?> {
        return dao.getSelectedRoutine()
    }

    override suspend fun deselectAllRoutines() {
        dao.deselectAllRoutines()
    }
}
