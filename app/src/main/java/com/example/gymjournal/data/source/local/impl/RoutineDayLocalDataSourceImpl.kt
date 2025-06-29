package com.example.gymjournal.data.source.local.impl

import com.example.gymjournal.data.local.dao.RoutineDayDao
import com.example.gymjournal.data.local.entity.RoutineDayEntity
import com.example.gymjournal.data.source.local.RoutineDayLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineDayLocalDataSourceImpl @Inject constructor(
    private val dao: RoutineDayDao
) : RoutineDayLocalDataSource {

    override suspend fun insertRoutineDay(day: RoutineDayEntity) {
        dao.insertDay(day)
    }

    override suspend fun insertRoutineDays(days: List<RoutineDayEntity>) {
        dao.insertDays(days)
    }

    override suspend fun updateRoutineDay(day: RoutineDayEntity) {
        dao.updateDay(day)
    }

    override suspend fun deleteRoutineDay(day: RoutineDayEntity) {
        dao.deleteDay(day)
    }

    override suspend fun deleteRoutineDaysByRoutine(routineId: Int) {
        dao.deleteDaysByRoutine(routineId)
    }

    override fun getRoutineDaysByRoutineId(routineId: Int): Flow<List<RoutineDayEntity>> {
        return dao.getDaysForRoutine(routineId)
    }
}