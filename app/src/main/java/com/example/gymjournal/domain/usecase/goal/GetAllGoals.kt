package com.example.gymjournal.domain.usecase.goal

import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow

class GetAllGoals(
    private val repository: GoalRepository
) {
    operator fun invoke(): Flow<List<Goal>> {
        return repository.getAllGoals()
    }
}