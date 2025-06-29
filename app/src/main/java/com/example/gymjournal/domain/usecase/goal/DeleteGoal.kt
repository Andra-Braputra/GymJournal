package com.example.gymjournal.domain.usecase.goal

import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.domain.repository.GoalRepository

class DeleteGoal(
    private val repository: GoalRepository
) {
    suspend operator fun invoke(goal: Goal) {
        repository.deleteGoal(goal)
    }
}