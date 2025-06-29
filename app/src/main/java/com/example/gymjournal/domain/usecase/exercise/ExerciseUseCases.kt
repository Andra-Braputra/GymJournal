package com.example.gymjournal.domain.usecase.exercise

data class ExerciseUseCases(
    val getExercises: GetExercises,
    val getExerciseById: GetExerciseById,
    val insertExercise: InsertExercise,
    val updateExercise: UpdateExercise,
    val deleteExercise: DeleteExercise,
    val syncExercises: SyncExercisesUseCase
)
