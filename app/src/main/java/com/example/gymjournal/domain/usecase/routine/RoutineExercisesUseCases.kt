package com.example.gymjournal.domain.usecase.routine

import com.example.gymjournal.domain.model.RoutineExercise
import com.example.gymjournal.domain.repository.routine.RoutineExerciseRepository

class InsertRoutineExerciseUseCase(private val repository: RoutineExerciseRepository) {
    suspend operator fun invoke(exercise: RoutineExercise) = repository.insertRoutineExercise(exercise)
}

class InsertRoutineExercisesUseCase(private val repository: RoutineExerciseRepository) {
    suspend operator fun invoke(exercises: List<RoutineExercise>) = repository.insertRoutineExercises(exercises)
}

class UpdateRoutineExerciseUseCase(private val repository: RoutineExerciseRepository) {
    suspend operator fun invoke(exercise: RoutineExercise) = repository.updateRoutineExercise(exercise)
}

class DeleteRoutineExerciseUseCase(private val repository: RoutineExerciseRepository) {
    suspend operator fun invoke(exercise: RoutineExercise) = repository.deleteRoutineExercise(exercise)
}

class DeleteRoutineExercisesByDayUseCase(private val repository: RoutineExerciseRepository) {
    suspend operator fun invoke(dayId: Int) = repository.deleteRoutineExercisesByDay(dayId)
}

class GetRoutineExercisesByDayUseCase(private val repository: RoutineExerciseRepository) {
    operator fun invoke(dayId: Int) = repository.getRoutineExercisesByDay(dayId)
}

class GetRoutineExerciseByIdUseCase(private val repository: RoutineExerciseRepository) {
    suspend operator fun invoke(id: Int) = repository.getRoutineExerciseById(id)
}

data class RoutineExercisesUseCases(
    val insert: InsertRoutineExerciseUseCase,
    val insertMany: InsertRoutineExercisesUseCase,
    val update: UpdateRoutineExerciseUseCase,
    val delete: DeleteRoutineExerciseUseCase,
    val deleteByDay: DeleteRoutineExercisesByDayUseCase,
    val getByDay: GetRoutineExercisesByDayUseCase,
    val getById: GetRoutineExerciseByIdUseCase
)
