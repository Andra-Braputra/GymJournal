package com.example.gymjournal.presentations.routine.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymjournal.domain.model.RoutineExercise


@Composable
fun ExerciseListSection(
    exercises: List<RoutineExercise>,
    onEdit: (RoutineExercise) -> Unit,
    onDelete: (RoutineExercise) -> Unit,
    onAdd: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = onAdd,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Exercise")
        }

        if (exercises.isEmpty()) {
            Text(
                "No exercises yet.",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            exercises.forEach { ex ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "Name: ${ex.exerciseName}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text("Sets: ${ex.sets}, Reps: ${ex.reps}, Weight: ${ex.weight} kg")

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            TextButton(onClick = { onEdit(ex) }) {
                                Text("Edit")
                            }
                            TextButton(onClick = { onDelete(ex) }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}

