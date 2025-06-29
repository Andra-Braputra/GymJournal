package com.example.gymjournal.presentations.routine.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoutineExerciseFormFields(
    sets: String,
    reps: String,
    weight: String,
    onSetsChange: (String) -> Unit,
    onRepsChange: (String) -> Unit,
    onWeightChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = sets,
            onValueChange = onSetsChange,
            label = { Text("Sets") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = reps,
            onValueChange = onRepsChange,
            label = { Text("Reps") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = weight,
            onValueChange = onWeightChange,
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
