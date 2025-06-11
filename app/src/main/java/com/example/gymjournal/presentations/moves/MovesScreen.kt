package com.example.gymjournal.presentations.moves

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.viewmodel.ExerciseViewModel
import com.example.gymjournal.ui.theme.AppTheme

@Composable
fun MovesScreen(
    navController: NavController,
    viewModel: ExerciseViewModel = hiltViewModel() // Inject ViewModel
) {
    var query by remember { mutableStateOf("") }
    val allMoves = viewModel.exercises

    val filteredMoves = allMoves.filter {
        it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = { TopNavBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Search") },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = { navController.navigate("AddMove") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(text = "Add Move")
                }
            }

            items(filteredMoves) { move ->
                MovesCard(
                    name = move.name,
                    type = move.description.ifBlank { "Unknown" },
                    modifier = Modifier.padding(vertical = 4.dp),
                    onDetailClick = { navController.navigate("detail/${move.id}") }
                )
            }
        }
    }
}



@Composable
fun MovesCard(
    name: String,
    type: String,
    modifier: Modifier = Modifier,
    onDetailClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onDetailClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Description: $type", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun MovesScreenPreview() {
    AppTheme {
        MovesScreen(navController = rememberNavController())
    }
}
