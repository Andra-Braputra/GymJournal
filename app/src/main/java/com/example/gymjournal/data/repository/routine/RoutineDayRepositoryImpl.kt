package com.example.gymjournal.data.repository.routine

import com.example.gymjournal.data.mapper.toDomain
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.data.source.local.RoutineDayLocalDataSource
import com.example.gymjournal.domain.model.RoutineDay
import com.example.gymjournal.domain.repository.routine.RoutineDayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineDayRepositoryImpl @Inject constructor(
    private val localDataSource: RoutineDayLocalDataSource
) : RoutineDayRepository {

    override suspend fun insertDay(day: RoutineDay) {
        localDataSource.insertRoutineDay(day.toEntity())
    }

    override suspend fun insertDays(days: List<RoutineDay>) {
        localDataSource.insertRoutineDays(days.map { it.toEntity() })
    }

    override suspend fun updateDay(day: RoutineDay) {
        localDataSource.updateRoutineDay(day.toEntity())
    }

    override suspend fun deleteDay(day: RoutineDay) {
        localDataSource.deleteRoutineDay(day.toEntity())
    }

    override suspend fun deleteDaysByRoutine(routineId: Int) {
        localDataSource.deleteRoutineDaysByRoutine(routineId)
    }

    override fun getDaysForRoutine(routineId: Int): Flow<List<RoutineDay>> {
        return localDataSource.getRoutineDaysByRoutineId(routineId)
            .map { it.map { entity -> entity.toDomain() } }
    }
}