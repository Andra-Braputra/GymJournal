package com.example.gymjournal.presentations.moves

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.domain.model.Exercise
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.viewmodel.ExerciseFormEvent
import com.example.gymjournal.presentations.viewmodel.ExerciseUiState
import com.example.gymjournal.presentations.viewmodel.ExerciseViewModel

@Composable
fun MovesScreen(
    navController: NavController,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }

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
        bottomBar = { BottomNavBar(navController = navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.ADD_MOVE)
            }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search by name, equipment, muscle...") },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )

            when (uiState) {
                is ExerciseUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ExerciseUiState.Error -> {
                    val message = (uiState as ExerciseUiState.Error).message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = message)
                    }
                }

                is ExerciseUiState.Success -> {
                    val allExercises = (uiState as ExerciseUiState.Success).exercises
                    val filteredMoves = allExercises.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.equipment.contains(query, ignoreCase = true) ||
                                it.muscle.contains(query, ignoreCase = true)
                    }

                    if (filteredMoves.isEmpty()) {
                        Text("No results found.", modifier = Modifier.padding(16.dp))
                    } else {
                        LazyColumn(contentPadding = PaddingValues(10.dp)) {
                            items(filteredMoves) { move ->
                                MovesCard(
                                    name = move.name,
                                    equipment = move.equipment.ifBlank { "Unknown" },
                                    muscles = move.muscle.ifBlank { "Unknown" },
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    onDetailClick = {
                                        navController.navigate(Routes.detailMove(move.id))
                                    },
                                    onDeleteClick = {
                                        selectedExercise = move
                                        showDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDialog && selectedExercise != null) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    selectedExercise = null
                },
                title = { Text("Delete Exercise") },
                text = { Text("Are you sure you want to delete '${selectedExercise?.name}'?") },
                confirmButton = {
                    TextButton(onClick = {
                        selectedExercise?.let { viewModel.deleteExercise(it) }
                        showDialog = false
                        selectedExercise = null
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        selectedExercise = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun MovesCard(
    name: String,
    equipment: String,
    muscles: String,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f).clickable { onDetailClick() }) {
                    Text(text = name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Equipment: $equipment", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Muscles: $muscles", style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
