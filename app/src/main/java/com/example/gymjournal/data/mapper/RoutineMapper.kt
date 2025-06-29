package com.example.gymjournal.data.mapper

import com.example.gymjournal.data.local.entity.RoutineDayEntity
import com.example.gymjournal.data.local.entity.RoutineEntity
import com.example.gymjournal.data.local.entity.RoutineExerciseEntity
import com.example.gymjournal.domain.model.Routine
import com.example.gymjournal.domain.model.RoutineDay
import com.example.gymjournal.domain.model.RoutineExercise

fun RoutineExerciseEntity.toDomain(): RoutineExercise =
    RoutineExercise(
        id = id,
        dayId = dayId,
        exerciseId = exerciseId,
        exerciseName = exerciseName,
        sets = sets,
        reps = reps,
        weight = weight
    )

fun RoutineExercise.toEntity(): RoutineExerciseEntity =
    RoutineExerciseEntity(
        id = id,
        dayId = dayId,
        exerciseId = exerciseId,
        exerciseName = exerciseName,
        sets = sets,
        reps = reps,
        weight = weight
    )

fun RoutineDayEntity.toDomain(): RoutineDay {
    return RoutineDay(
        id = this.id,
        routineId = this.routineId,
        name = this.name,
        order = this.order,
        isRestDay = this.isRestDay
    )
}
fun RoutineDay.toEntity(): RoutineDayEntity {
    return RoutineDayEntity(
        id = this.id,
        routineId = this.routineId,
        name = this.name,
        order = this.order,
        isRestDay = this.isRestDay
    )
}
fun RoutineEntity.toDomain(): Routine =
    Routine(id = id, name = name, isSelected = isSelected)

fun Routine.toEntity(): RoutineEntity =
    RoutineEntity(id = id, name = name, isSelected = isSelected)