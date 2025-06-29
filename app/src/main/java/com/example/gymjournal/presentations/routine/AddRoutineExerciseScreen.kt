package com.example.gymjournal.presentations.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
    // 🔽 Di sinilah kamu menaruhnya
    val uiState by exerciseViewModel.uiState.collectAsState()
    val allExercises = if (uiState is ExerciseUiState.Success) {
        (uiState as ExerciseUiState.Success).exercises
    } else emptyList()

    val selectedExercise = remember(allExercises) {
        allExercises.find { it.id == exerciseId }
    }

    // 🔽 Lanjut dengan sisa state dan UI seperti ini
    var sets by rememberSaveable { mutableStateOf("") }
    var reps by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Exercise to Day") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Exercise: ${selectedExercise?.name ?: "Exercise not found"}",
                style = MaterialTheme.typography.titleMedium
            )

            RoutineExerciseFormFields(
                sets = sets,
                reps = reps,
                weight = weight,
                onSetsChange = { sets = it },
                onRepsChange = { reps = it },
                onWeightChange = { weight = it }
            )

            Button(
                onClick = {
                    val isValid = sets.toIntOrNull() != null && reps.toIntOrNull() != null
                    if (selectedExercise != null && isValid) {
                        val newExercise = RoutineExercise(
                            id = 0,
                            dayId = dayId,
                            exerciseId = selectedExercise.id,
                            exerciseName = selectedExercise.name,
                            sets = sets.toInt(),
                            reps = reps.toInt(),
                            weight = weight.toFloatOrNull() ?: 0f
                        )
                        viewModel.insertExerciseToDay(newExercise)
                        navController.popBackStack()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please select a valid exercise and enter valid sets/reps.")
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
