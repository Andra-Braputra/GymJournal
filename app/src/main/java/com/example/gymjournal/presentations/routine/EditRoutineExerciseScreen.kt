package com.example.gymjournal.presentations.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRoutineExerciseScreen(
    navController: NavController,
    exerciseId: Int,
    viewModel: RoutineViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var exercise by remember { mutableStateOf<RoutineExercise?>(null) }
    var sets by rememberSaveable { mutableStateOf("") }
    var reps by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(exerciseId) {
        viewModel.getRoutineExerciseById(exerciseId)?.let {
            exercise = it
            sets = it.sets.toString()
            reps = it.reps.toString()
            weight = it.weight.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Exercise") })
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
                    "Exercise: ${exercise?.exerciseName ?: ""}",
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
                        if (exercise != null && sets.toIntOrNull() != null && reps.toIntOrNull() != null) {
                            val updated = exercise!!.copy(
                                sets = sets.toInt(),
                                reps = reps.toInt(),
                                weight = weight.toFloatOrNull() ?: 0f
                            )
                            viewModel.updateRoutineExercise(updated)
                            navController.popBackStack()
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Fill valid sets and reps.")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Exercise")
                }
            }
        }
    }
}
