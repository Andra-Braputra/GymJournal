package com.example.gymjournal.presentations.workout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gymjournal.domain.model.RoutineExercise
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
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
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

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
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = selectedRoutine?.name ?: "Routine Name",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    if (exercises.isEmpty()) {
                        item {
                            Text("No exercises for today.", style = MaterialTheme.typography.bodyLarge)
                        }
                    } else {
                        val exercise = exercises[currentIndex]
                        item {
                            ExerciseCard(
                                exercise = exercise,
                                onPrevious = {
                                    if (currentIndex > 0) {
                                        currentIndex--
                                        remainingTime = totalTime
                                    }
                                },
                                onNext = {
                                    if (currentIndex < exercises.size - 1) {
                                        currentIndex++
                                        remainingTime = totalTime
                                    }
                                }
                            )
                        }
                    }
                }
                if (exercises.isNotEmpty()) {
                    TimerPanel(
                        remainingTime = remainingTime,
                        totalTime = totalTime,
                        isRunning = isRunning,
                        customTimeInput = customTimeInput,
                        onCustomTimeChange = { customTimeInput = it },
                        onSetTime = { input ->
                            input.toIntOrNull()?.takeIf { it > 0 }?.let {
                                totalTime = it
                                remainingTime = it
                            }
                        },
                        onStartPause = { isRunning = !isRunning },
                        onStop = {
                            isRunning = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Workout finished! 💪")
                            }
                        }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = selectedRoutine?.name ?: "Routine Name",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                if (exercises.isEmpty()) {
                    item {
                        Text("No exercises for today.", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    val exercise = exercises[currentIndex]
                    item {
                        ExerciseCard(
                            exercise = exercise,
                            onPrevious = {
                                if (currentIndex > 0) {
                                    currentIndex--
                                    remainingTime = totalTime
                                }
                            },
                            onNext = {
                                if (currentIndex < exercises.size - 1) {
                                    currentIndex++
                                    remainingTime = totalTime
                                }
                            }
                        )
                    }
                    item {
                        TimerPanel(
                            remainingTime = remainingTime,
                            totalTime = totalTime,
                            isRunning = isRunning,
                            customTimeInput = customTimeInput,
                            onCustomTimeChange = { customTimeInput = it },
                            onSetTime = { input ->
                                input.toIntOrNull()?.takeIf { it > 0 }?.let {
                                    totalTime = it
                                    remainingTime = it
                                }
                            },
                            onStartPause = { isRunning = !isRunning },
                            onStop = {
                                isRunning = false
                                scope.launch {
                                    snackbarHostState.showSnackbar("Workout finished! 💪")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimerPanel(
    remainingTime: Int,
    totalTime: Int,
    isRunning: Boolean,
    customTimeInput: String,
    onCustomTimeChange: (String) -> Unit,
    onSetTime: (String) -> Unit,
    onStartPause: () -> Unit,
    onStop: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%02d:%02d", remainingTime / 60, remainingTime % 60),
            style = MaterialTheme.typography.displayLarge
        )
        LinearProgressIndicator(
            progress = { if (totalTime == 0) 0f else remainingTime.toFloat() / totalTime.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = { onSetTime("30") }) { Text("30s") }
            OutlinedButton(onClick = { onSetTime("60") }) { Text("60s") }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = customTimeInput,
                onValueChange = onCustomTimeChange,
                label = { Text("Custom (sec)") },
                singleLine = true,
                modifier = Modifier.width(140.dp)
            )
            OutlinedButton(onClick = { onSetTime(customTimeInput) }) {
                Text("Set")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onStartPause,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) Color.Red else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (isRunning) "Pause" else "Start")
            }

            Button(onClick = onStop) {
                Text("Stop")
            }
        }
    }
}

@Composable
fun ExerciseCard(exercise: RoutineExercise, onPrevious: () -> Unit, onNext: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                OutlinedButton(onClick = onPrevious) { Text("Previous") }
                OutlinedButton(onClick = onNext) { Text("Next") }
            }
        }
    }
}
