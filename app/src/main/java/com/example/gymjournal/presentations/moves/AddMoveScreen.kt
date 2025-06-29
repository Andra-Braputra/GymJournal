package com.example.gymjournal.presentations.moves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.viewmodel.ExerciseFormEvent
import com.example.gymjournal.presentations.viewmodel.ExerciseViewModel

@Composable
fun AddMoveScreen(
    navController: NavController,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    var name by rememberSaveable { mutableStateOf("") }
    var muscle by rememberSaveable { mutableStateOf("") }
    var equipment by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    val isFormValid = name.isNotBlank() && muscle.isNotBlank() && equipment.isNotBlank()

    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ExerciseFormEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        topBar = { TopNavBar(navController = navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Add New Move", style = MaterialTheme.typography.titleLarge)
            }

            item {
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
            }

            item {
                Button(
                    onClick = {
                        val newExercise = Exercise(
                            id = 0,
                            name = name,
                            muscle = muscle,
                            equipment = equipment,
                            description = description
                        )
                        viewModel.insertExercise(newExercise)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isFormValid
                ) {
                    Text("Save")
                }
            }

            if (!isFormValid) {
                item {
                    Text(
                        text = "Please fill all required fields (name, muscle, equipment).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}