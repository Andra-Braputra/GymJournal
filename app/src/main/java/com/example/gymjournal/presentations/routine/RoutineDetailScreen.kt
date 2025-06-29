package com.example.gymjournal.presentations.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.domain.model.Routine
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.routine.comp.ExerciseListSection
import com.example.gymjournal.presentations.routine.comp.RoutineDaySelector
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
import kotlinx.coroutines.launch

@Composable
fun RoutineDetailScreen(
    navController: NavController,
    routineId: Int,
    viewModel: RoutineViewModel = hiltViewModel()
) {
    val selectedDay by viewModel.currentDay.collectAsState()
    val routineDays by viewModel.routineDays.collectAsState()
    val exercisesByDay by viewModel.routineExercisesByDay.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var routineName by remember { mutableStateOf("") }
    val showCreateForm = routineId == -1

    LaunchedEffect(routineId) {
        if (routineId != -1) {
            viewModel.selectRoutineById(routineId)
        }
    }

    Scaffold(
        topBar = { TopNavBar(navController = navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (showCreateForm) {
                item {
                    Column {
                        Text("Create New Routine", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = routineName,
                            onValueChange = { routineName = it },
                            label = { Text("Routine Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                if (routineName.isNotBlank()) {
                                    viewModel.insertRoutine(Routine(name = routineName))
                                    routineName = ""
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Routine created")
                                    }
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Please enter a name")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Routine")
                        }
                    }
                }
            } else {
                // Day Selector
                item {
                    RoutineDaySelector(
                        days = routineDays,
                        currentDay = selectedDay,
                        onDaySelected = { viewModel.selectDay(it) }
                    )
                }

                selectedDay?.let { day ->
                    // Rest/Work Day toggle
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (day.isRestDay) "Rest Day" else "Workout Day",
                                style = MaterialTheme.typography.titleMedium
                            )
                            TextButton(onClick = {
                                viewModel.setDayAsRest(day.id, !day.isRestDay)
                            }) {
                                Text(if (day.isRestDay) "Set as Work Day" else "Set as Rest Day")
                            }
                        }
                    }

                    if (!day.isRestDay) {
                        item {
                            ExerciseListSection(
                                exercises = exercisesByDay[day.id] ?: emptyList(),
                                onEdit = { navController.navigate(Routes.editExercise(it.id)) },
                                onDelete = { viewModel.deleteRoutineExercise(it) },
                                onAdd = {
                                    navController.navigate(Routes.selectMove(day.id))
                                }
                            )
                        }
                    } else {
                        item {
                            Text(
                                "No exercises. This is a rest day.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                } ?: item {
                    Text("No day selected.", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}