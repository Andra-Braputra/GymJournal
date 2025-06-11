package com.example.gymjournal.presentations.goals

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Goal(
    val id: Int,
    val name: String,
    val detail: String,
    val deadline: String
)

open class GoalsViewModel : ViewModel() {
    var goals = mutableStateListOf<Goal>()
        private set

    private var nextId = 1

    fun addGoal(name: String, detail: String, deadline: String) {
        goals.add(Goal(nextId++, name, detail, deadline))
    }

    fun removeGoal(goal: Goal) {
        goals.remove(goal)
    }

    fun editGoal(updated: Goal) {
        val index = goals.indexOfFirst { it.id == updated.id }
        if (index >= 0) goals[index] = updated
    }
}
