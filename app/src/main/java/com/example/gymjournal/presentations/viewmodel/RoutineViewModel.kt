package com.example.gymjournal.presentations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymjournal.domain.model.Routine
import com.example.gymjournal.domain.model.RoutineDay
import com.example.gymjournal.domain.model.RoutineExercise
import com.example.gymjournal.domain.usecase.routine.RoutineDayUseCases
import com.example.gymjournal.domain.usecase.routine.RoutineExercisesUseCases
import com.example.gymjournal.domain.usecase.routine.RoutineUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val routineUseCases: RoutineUseCases,
    private val dayUseCases: RoutineDayUseCases,
    private val exerciseUseCases: RoutineExercisesUseCases
) : ViewModel() {

    private val _allRoutines = MutableStateFlow<List<Routine>>(emptyList())
    val allRoutines: StateFlow<List<Routine>> = _allRoutines

    private val _selectedRoutine = MutableStateFlow<Routine?>(null)
    val selectedRoutine: StateFlow<Routine?> = _selectedRoutine

    private val _routineDays = MutableStateFlow<List<RoutineDay>>(emptyList())
    val routineDays: StateFlow<List<RoutineDay>> = _routineDays

    private val _routineExercisesByDay = MutableStateFlow<Map<Int, List<RoutineExercise>>>(emptyMap())
    val routineExercisesByDay: StateFlow<Map<Int, List<RoutineExercise>>> = _routineExercisesByDay

    private val _currentDay = MutableStateFlow<RoutineDay?>(null)
    val currentDay: StateFlow<RoutineDay?> = _currentDay

    init {
        getAllRoutines()
        getSelectedRoutine()
    }

    fun getRoutineExerciseById(id: Int): RoutineExercise? {
        return routineExercisesByDay.value.values.flatten().find { it.id == id }
    }

    fun selectRoutineById(id: Int) {
        viewModelScope.launch {
            val routine = routineUseCases.getRoutineById(id)
            _selectedRoutine.value = routine
            routine?.let {
                fetchRoutineDays(it.id)
            }
        }
    }

    fun getAllRoutines() {
        viewModelScope.launch {
            routineUseCases.getAllRoutines().collect { routines ->
                _allRoutines.value = routines
            }
        }
    }

    private fun getSelectedRoutine() {
        viewModelScope.launch {
            routineUseCases.getSelectedRoutine().collect { routine ->
                _selectedRoutine.value = routine
                routine?.let { fetchRoutineDays(it.id) }
            }
        }
    }

    private fun fetchRoutineDays(routineId: Int) {
        viewModelScope.launch {
            dayUseCases.getDaysForRoutine(routineId).collect { days ->
                _routineDays.value = days

                if (days.isNotEmpty()) {
                    val currentDay = _currentDay.value
                    val updatedCurrentDay = days.find { it.id == currentDay?.id } ?: days.first()
                    _currentDay.value = updatedCurrentDay
                }

                days.forEach { fetchExercisesForDay(it.id) }
            }
        }
    }

    private fun fetchExercisesForDay(dayId: Int) {
        viewModelScope.launch {
            exerciseUseCases.getByDay(dayId).collect { exercises ->
                _routineExercisesByDay.update { currentMap ->
                    currentMap + (dayId to exercises)
                }
            }
        }
    }

    fun refreshSelectedRoutine() {
        getSelectedRoutine()
    }

    fun selectRoutine(routine: Routine) {
        viewModelScope.launch {
            routineUseCases.deselectAllRoutines()
            routineUseCases.updateRoutine(routine.copy(isSelected = true))
            getSelectedRoutine()
        }
    }

    fun insertRoutine(routine: Routine) {
        viewModelScope.launch {
            val insertedId = routineUseCases.insertRoutine(routine).toInt()

            routineUseCases.deselectAllRoutines()
            routineUseCases.updateRoutine(routine.copy(id = insertedId, isSelected = true))

            insertDefaultDays(insertedId)

            getAllRoutines()
            selectRoutineById(insertedId)
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            routineUseCases.deleteRoutine(routine)
            getAllRoutines()
        }
    }


    fun setDayAsRest(dayId: Int, isRest: Boolean) {
        val updatedDay = _routineDays.value.find { it.id == dayId }?.copy(isRestDay = isRest)
        updatedDay?.let {
            viewModelScope.launch {
                dayUseCases.updateDay(it)
                refreshSelectedRoutine()
            }
        }
    }

    fun insertExerciseToDay(exercise: RoutineExercise) {
        viewModelScope.launch {
            exerciseUseCases.insert(exercise)
            fetchExercisesForDay(exercise.dayId)
        }
    }

    fun updateRoutineExercise(exercise: RoutineExercise) {
        viewModelScope.launch {
            exerciseUseCases.update(exercise)
            fetchExercisesForDay(exercise.dayId)
        }
    }

    fun deleteRoutineExercise(exercise: RoutineExercise) {
        viewModelScope.launch {
            exerciseUseCases.delete(exercise)
            fetchExercisesForDay(exercise.dayId)
        }
    }

    fun selectDay(day: RoutineDay) {
        _currentDay.value = day
    }

    private fun insertDefaultDays(routineId: Int) {
        val daysOfWeek = listOf(
            "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"
        )

        val days = daysOfWeek.mapIndexed { index, name ->
            RoutineDay(
                id = 0, // autoGenerate
                routineId = routineId,
                name = name,
                order = index,
                isRestDay = true
            )
        }

        viewModelScope.launch {
            dayUseCases.insertDays(days)
            refreshSelectedRoutine()
        }
    }
}