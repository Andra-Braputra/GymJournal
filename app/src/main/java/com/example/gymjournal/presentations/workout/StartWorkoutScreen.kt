package com.example.gymjournal.presentations.workout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
import com.example.gymjournal.presentations.workout.comp.TimerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartWorkoutScreen(
    viewModel: RoutineViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val selectedRoutine by viewModel.selectedRoutine.collectAsState()
    val routineDays by viewModel.routineDays.collectAsState()
    val exercisesByDay by viewModel.routineExercisesByDay.collectAsState()

    val currentDayName = remember {
        LocalDate.now().dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    }

    val currentDay = routineDays.find { it.name == currentDayName }
    val exercises = currentDay?.let { exercisesByDay[it.id] } ?: emptyList()

    var currentIndex by remember { mutableStateOf(0) }
    var totalTime by remember { mutableStateOf(30) }
    var remainingTime by remember { mutableStateOf(30) }
    var isRunning by remember { mutableStateOf(false) }
    var customTimeInput by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isRunning, remainingTime, currentIndex) {
        if (isRunning && remainingTime > 0) {
            delay(1000)
            remainingTime--
        } else if (isRunning && remainingTime == 0) {
            if (currentIndex < exercises.size - 1) {
                currentIndex++
                remainingTime = totalTime
            } else {
                isRunning = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Session") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = selectedRoutine?.name ?: "Routine Name",
                style = MaterialTheme.typography.headlineSmall
            )

            if (exercises.isEmpty()) {
                Text("No exercises for today.", style = MaterialTheme.typography.bodyLarge)
            } else {
                val exercise = exercises[currentIndex]

                // Exercise Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(exercise.exerciseName, style = MaterialTheme.typography.headlineSmall)
                        Text("${exercise.weight} kg", style = MaterialTheme.typography.bodyMedium)
                        Text("${exercise.reps} Reps", style = MaterialTheme.typography.bodyMedium)
                        Text("${exercise.sets} Sets", style = MaterialTheme.typography.bodyMedium)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(
                                onClick = {
                                    if (currentIndex > 0) {
                                        currentIndex--
                                        remainingTime = totalTime
                                    }
                                }
                            ) { Text("Previous") }

                            OutlinedButton(
                                onClick = {
                                    if (currentIndex < exercises.size - 1) {
                                        currentIndex++
                                        remainingTime = totalTime
                                    }
                                }
                            ) { Text("Next") }
                        }
                    }
                }

                // Timer control
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = {
                            totalTime = 30
                            remainingTime = 30
                        }) { Text("30s Timer") }

                        OutlinedButton(onClick = {
                            totalTime = 60
                            remainingTime = 60
                        }) { Text("60s Timer") }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = customTimeInput,
                            onValueChange = { customTimeInput = it },
                            label = { Text("Custom (sec)") },
                            singleLine = true,
                            modifier = Modifier.width(140.dp)
                        )
                        OutlinedButton(onClick = {
                            customTimeInput.toIntOrNull()?.takeIf { it > 0 }?.let {
                                totalTime = it
                                remainingTime = it
                                customTimeInput = ""
                            }
                        }) {
                            Text("Set")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Timer display
                TimerDisplay(
                    timerState = TimerState(totalTime, remainingTime, isRunning),
                    toggleStartStop = { isRunning = !isRunning }
                )

                // Action buttons
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { isRunning = !isRunning },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRunning) Color.Red else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(if (isRunning) "Pause" else "Start")
                    }

                    Button(
                        onClick = {
                            isRunning = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Workout finished! 💪")
                            }
                        }
                    ) {
                        Text("Stop")
                    }
                }
            }
        }
    }
}
