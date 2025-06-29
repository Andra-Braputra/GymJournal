package com.example.gymjournal.data.mapper

import com.example.gymjournal.data.local.entity.GoalEntity
import com.example.gymjournal.domain.model.Goal

fun GoalEntity.toGoal(): Goal {
    return Goal(
        id = id,
        name = name,
        detail = detail,
        deadline = deadline
    )
}

fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = id,
        name = name,
        detail = detail,
        deadline = deadline
    )
}