package com.example.gymjournal.domain.usecase.goal

data class GoalUseCases(
    val getAllGoals: GetAllGoals,
    val insertGoal: InsertGoal,
    val updateGoal: UpdateGoal,
    val deleteGoal: DeleteGoal,
    val getGoalById: GetGoalById
)