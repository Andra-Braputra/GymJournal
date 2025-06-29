package com.example.gymjournal.domain.usecase.goal

import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.domain.repository.GoalRepository

class GetGoalById(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(id: Int): Goal? {
        return repository.getGoalById(id)
    }
}