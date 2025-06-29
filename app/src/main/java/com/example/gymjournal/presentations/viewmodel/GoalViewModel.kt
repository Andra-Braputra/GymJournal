package com.example.gymjournal.presentations.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.domain.usecase.goal.GoalUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalUiState(val goals: List<Goal> = emptyList())

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalUseCases: GoalUseCases
) : ViewModel() {

    val uiState: StateFlow<GoalUiState> = goalUseCases.getAllGoals()
        .map { GoalUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GoalUiState()
        )

    fun addGoal(goal: Goal) = viewModelScope.launch {
        goalUseCases.insertGoal(goal)
    }

    fun updateGoal(goal: Goal) = viewModelScope.launch {
        goalUseCases.updateGoal(goal)
    }

    fun deleteGoal(goal: Goal) = viewModelScope.launch {
        goalUseCases.deleteGoal(goal)
    }

    suspend fun getGoalById(id: Int): Goal? {
        return goalUseCases.getGoalById(id)
    }
}