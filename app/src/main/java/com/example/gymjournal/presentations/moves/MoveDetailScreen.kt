package com.example.gymjournal.presentations.moves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.viewmodel.ExerciseViewModel
import kotlinx.coroutines.launch

@Composable
fun MoveDetailScreen(
    navController: NavController,
    exerciseId: Int,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    var exercise by remember { mutableStateOf<Exercise?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var name by rememberSaveable { mutableStateOf("") }
    var muscle by rememberSaveable { mutableStateOf("") }
    var equipment by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isFormValid = name.isNotBlank() && muscle.isNotBlank() && equipment.isNotBlank()

    LaunchedEffect(exerciseId) {
        viewModel.getExerciseById(exerciseId)?.let {
            exercise = it
            name = it.name
            muscle = it.muscle
            equipment = it.equipment
            description = it.description
        }
    }

    Scaffold(
        topBar = { TopNavBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        exercise?.let { ex ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = if (isEditing) "Edit Move" else "Move Detail",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                item {
                    if (isEditing) {
                        ExerciseFormFields(
                            name = name,
                            onNameChange = { name = it },
                            muscle = muscle,
                            onMuscleChange = { muscle = it },
                            equipment = equipment,
                            onEquipmentChange = { equipment = it },
                            description = description,
                            onDescriptionChange = { description = it }
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Name: ${ex.name}", style = MaterialTheme.typography.bodyLarge)
                            Text("Muscle Group: ${ex.muscle}", style = MaterialTheme.typography.bodyLarge)
                            Text("Equipment: ${ex.equipment}", style = MaterialTheme.typography.bodyLarge)
                            Text("Description: ${ex.description}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isEditing) {
                            Button(
                                onClick = {
                                    val updated = ex.copy(
                                        name = name,
                                        muscle = muscle,
                                        equipment = equipment,
                                        description = description
                                    )
                                    viewModel.updateExercise(updated)
                                    exercise = updated
                                    isEditing = false
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Exercise updated")
                                    }
                                },
                                enabled = isFormValid
                            ) {
                                Text("Save")
                            }
                        } else {
                            Button(onClick = { isEditing = true }) {
                                Text("Edit")
                            }
                        }

                        OutlinedButton(onClick = { showDeleteDialog = true }) {
                            Text("Delete")
                        }
                    }
                }

                if (isEditing && !isFormValid) {
                    item {
                        Text(
                            text = "All fields must be filled.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }

    if (showDeleteDialog && exercise != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Exercise") },
            text = { Text("Are you sure you want to delete this exercise?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteExercise(exercise!!)
                    navController.popBackStack()
                    scope.launch {
                        snackbarHostState.showSnackbar("Exercise deleted")
                    }
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
