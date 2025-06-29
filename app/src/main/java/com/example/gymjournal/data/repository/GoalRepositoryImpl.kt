package com.example.gymjournal.data.repository

import com.example.gymjournal.data.local.dao.GoalDao
import com.example.gymjournal.data.mapper.toEntity
import com.example.gymjournal.data.mapper.toGoal
import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val dao: GoalDao
) : GoalRepository {

    override fun getAllGoals(): Flow<List<Goal>> {
        return dao.getAllGoals().map { list -> list.map { it.toGoal() } }
    }

    override suspend fun insertGoal(goal: Goal) {
        dao.insertGoal(goal.toEntity())
    }

    override suspend fun updateGoal(goal: Goal) {
        dao.updateGoal(goal.toEntity())
    }

    override suspend fun deleteGoal(goal: Goal) {
        dao.deleteGoal(goal.toEntity())
    }

    override suspend fun getGoalById(id: Int): Goal? {
        return dao.getGoalById(id)?.toGoal()
    }
}
