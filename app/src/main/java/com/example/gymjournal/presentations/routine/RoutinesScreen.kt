package com.example.gymjournal.presentations.routine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
import kotlinx.coroutines.launch

@Composable
fun RoutinesScreen(
    navController: NavController,
    viewModel: RoutineViewModel = hiltViewModel()
) {
    val routines by viewModel.allRoutines.collectAsState()
    val selectedRoutine by viewModel.selectedRoutine.collectAsState()
    var query by remember { mutableStateOf("") }
    var showDeleteDialogFor by remember { mutableStateOf<Routine?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val filteredRoutines = routines.filter { it.name.contains(query, ignoreCase = true) }

    Scaffold(
        topBar = { TopNavBar(navController) },
        bottomBar = { BottomNavBar(navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search Routines") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Routes.routineDetail(-1)) // for new routine
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            ) {
                Text("Add Routine")
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(filteredRoutines) { routine ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Routes.routineDetail(routine.id))
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = routine.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            if (routine.id == selectedRoutine?.id) {
                                Text(
                                    text = "Currently Selected",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = {
                                    viewModel.selectRoutine(routine)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Selected '${routine.name}'")
                                    }
                                }) {
                                    Text("Select")
                                }

                                TextButton(onClick = {
                                    showDeleteDialogFor = routine
                                }) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    showDeleteDialogFor?.let { routine ->
        AlertDialog(
            onDismissRequest = { showDeleteDialogFor = null },
            title = { Text("Delete Routine") },
            text = { Text("Are you sure you want to delete '${routine.name}'?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteRoutine(routine)
                    showDeleteDialogFor = null
                    scope.launch {
                        snackbarHostState.showSnackbar("Deleted '${routine.name}'")
                    }
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialogFor = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
