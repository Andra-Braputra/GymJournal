package com.example.gymjournal.moves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymjournal.components.TopNavBar
import com.example.gymjournal.ui.theme.AppTheme

@Composable
fun AddMoveScreen(
    onSave: (String, String) -> Unit = { _, _ -> }
    ,navController: NavController
) {
    var name by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = { TopNavBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Add New Move", style = MaterialTheme.typography.headlineSmall)

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Move Name") },
                placeholder = { Text("e.g., Push Up") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Move Type") },
                placeholder = { Text("e.g., Reps or Timed") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onSave(name, type) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun AddMovePreview() {
    AppTheme {
        AddMoveScreen(navController = NavController(LocalContext.current))
    }
}
