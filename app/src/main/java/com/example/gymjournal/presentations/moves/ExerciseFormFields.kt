package com.example.gymjournal.presentations.moves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseFormFields(
    name: String,
    onNameChange: (String) -> Unit,
    muscle: String,
    onMuscleChange: (String) -> Unit,
    equipment: String,
    onEquipmentChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    enabled: Boolean = true
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = muscle,
            onValueChange = onMuscleChange,
            label = { Text("Muscle Group") },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = equipment,
            onValueChange = onEquipmentChange,
            label = { Text("Equipment") },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
