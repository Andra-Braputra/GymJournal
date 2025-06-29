package com.example.gymjournal.presentations.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.domain.model.RoutineExercise
import com.example.gymjournal.presentations.routine.comp.RoutineExerciseFormFields
import com.example.gymjournal.presentations.viewmodel.ExerciseUiState
import com.example.gymjournal.presentations.viewmodel.ExerciseViewModel
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoutineExerciseScreen(
    navController: NavController,
    routineId: Int,
    dayId: Int,
    exerciseId: Int,
    viewModel: RoutineViewModel = hiltViewModel(),
    exerciseViewModel: ExerciseViewModel = hiltViewModel()
) {
    val uiState by exerciseViewModel.uiState.collectAsState()
    val exercises = (uiState as? ExerciseUiState.Success)?.exercises ?: emptyList()
    val selectedExercise = remember(exercises) {
        exercises.find { it.id == exerciseId }
    }

    var sets by rememberSaveable { mutableStateOf("") }
    var reps by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Exercise to Day") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Exercise: ${selectedExercise?.name ?: "Not Found"}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                RoutineExerciseFormFields(
                    sets = sets,
                    reps = reps,
                    weight = weight,
                    onSetsChange = { sets = it },
                    onRepsChange = { reps = it },
                    onWeightChange = { weight = it }
                )
            }

            item {
                Button(
                    onClick = {
                        val validSets = sets.toIntOrNull()
                        val validReps = reps.toIntOrNull()
                        val validWeight = weight.toFloatOrNull() ?: 0f

                        if (selectedExercise != null && validSets != null && validReps != null) {
                            val newExercise = RoutineExercise(
                                id = 0,
                                dayId = dayId,
                                exerciseId = selectedExercise.id,
                                exerciseName = selectedExercise.name,
                                sets = validSets,
                                reps = validReps,
                                weight = validWeight
                            )
                            viewModel.insertExerciseToDay(newExercise)
                            navController.popBackStack()
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "Please enter valid sets and reps, and make sure exercise is selected."
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Exercise")
                }
            }
        }
    }
}

