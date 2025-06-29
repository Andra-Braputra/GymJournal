package com.example.gymjournal.data.mapper

import com.example.gymjournal.data.dto.ExerciseDto
import com.example.gymjournal.data.local.entity.ExerciseEntity
import com.example.gymjournal.domain.model.Exercise

// DTO → Domain
fun ExerciseDto.toDomain(): Exercise = Exercise(
    id = id,
    name = name,
    muscle = muscle,
    equipment = equipment,
    description = description
)

// Domain → Entity
fun Exercise.toEntity(): ExerciseEntity = ExerciseEntity(
    id = id,
    name = name,
    muscle = muscle,
    equipment = equipment,
    description = description
)

// Entity → Domain
fun ExerciseEntity.toDomain(): Exercise = Exercise(
    id = id,
    name = name,
    muscle = muscle,
    equipment = equipment,
    description = description
)