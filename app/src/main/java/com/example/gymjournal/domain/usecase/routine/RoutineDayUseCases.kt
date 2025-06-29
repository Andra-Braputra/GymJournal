package com.example.gymjournal.domain.usecase.routine

import com.example.gymjournal.domain.model.RoutineDay
import com.example.gymjournal.domain.repository.routine.RoutineDayRepository

class InsertRoutineDayUseCase(private val repository: RoutineDayRepository) {
    suspend operator fun invoke(day: RoutineDay) = repository.insertDay(day)
}

class InsertRoutineDaysUseCase(private val repository: RoutineDayRepository) {
    suspend operator fun invoke(days: List<RoutineDay>) = repository.insertDays(days)
}

class UpdateRoutineDayUseCase(private val repository: RoutineDayRepository) {
    suspend operator fun invoke(day: RoutineDay) = repository.updateDay(day)
}

class DeleteRoutineDayUseCase(private val repository: RoutineDayRepository) {
    suspend operator fun invoke(day: RoutineDay) = repository.deleteDay(day)
}

class GetRoutineDaysByRoutineIdUseCase(private val repository: RoutineDayRepository) {
    operator fun invoke(routineId: Int) = repository.getDaysForRoutine(routineId)
}

data class RoutineDayUseCases(
    val insertDay: InsertRoutineDayUseCase,
    val insertDays: InsertRoutineDaysUseCase,
    val updateDay: UpdateRoutineDayUseCase,
    val deleteDay: DeleteRoutineDayUseCase,
    val getDaysForRoutine: GetRoutineDaysByRoutineIdUseCase
)
