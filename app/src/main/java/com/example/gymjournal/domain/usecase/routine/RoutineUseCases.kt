package com.example.gymjournal.domain.usecase.routine

import com.example.gymjournal.domain.model.Routine
import com.example.gymjournal.domain.repository.routine.RoutineRepository
import kotlinx.coroutines.flow.firstOrNull

class InsertRoutineUseCase(private val repository: RoutineRepository) {
    suspend operator fun invoke(routine: Routine): Long {
        return repository.insertRoutine(routine)
    }
}

class UpdateRoutineUseCase(private val repository: RoutineRepository) {
    suspend operator fun invoke(routine: Routine) = repository.updateRoutine(routine)
}

class DeleteRoutineUseCase(private val repository: RoutineRepository) {
    suspend operator fun invoke(routine: Routine) = repository.deleteRoutine(routine)
}

class GetAllRoutinesUseCase(private val repository: RoutineRepository) {
    operator fun invoke() = repository.getAllRoutines()
}

class GetSelectedRoutineUseCase(private val repository: RoutineRepository) {
    operator fun invoke() = repository.getSelectedRoutine()
}

class DeselectAllRoutinesUseCase(private val repository: RoutineRepository) {
    suspend operator fun invoke() = repository.deselectAllRoutines()
}

class GetRoutineByIdUseCase(
    private val repository: RoutineRepository
) {
    suspend operator fun invoke(id: Int): Routine? {
        return repository.getAllRoutines()
            .firstOrNull()
            ?.find { it.id == id }
    }
}

data class RoutineUseCases(
    val insertRoutine: InsertRoutineUseCase,
    val updateRoutine: UpdateRoutineUseCase,
    val deleteRoutine: DeleteRoutineUseCase,
    val getAllRoutines: GetAllRoutinesUseCase,
    val getSelectedRoutine: GetSelectedRoutineUseCase,
    val deselectAllRoutines: DeselectAllRoutinesUseCase,
    val getRoutineById: GetRoutineByIdUseCase
)