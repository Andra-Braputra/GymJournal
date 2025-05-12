package com.example.gymjournal.routine

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymjournal.components.BottomNavBar
import com.example.gymjournal.components.TopNavBar
import com.example.gymjournal.ui.theme.AppTheme

data class Routines(
    val id: Int,
    val name: String
)

class Datasource {
    fun loadRoutine(): List<Routines> {
        return listOf(
            Routines(id = 1, name = "Basic Routine"),
            Routines(id = 2, name = "Weight Loss Routine"),
            Routines(id = 3, name = "Cardio Routine")
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutinesScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    val allRoutines = remember { Datasource().loadRoutine() }
    val filteredRoutines = allRoutines.filter {
        it.name.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = { TopNavBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {},
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {}

            Button(
                onClick = { navController.navigate(" ") },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Add Routine")
            }

            RoutineList(
                routineList = filteredRoutines,
                onDetailClick = { id ->
                    navController.navigate("/$id")
                }
            )
        }
    }
}

@Composable
fun RoutineList(
    routineList: List<Routines>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp)
    ) {
        items(routineList) { routine ->
            RoutineCard(
                routines = routine,
                modifier = Modifier.padding(vertical = 4.dp),
                onDetailClick = { onDetailClick(routine.id) }
            )
        }
    }
}

@Composable
fun RoutineCard(
    routines: Routines,
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
            Text(text = routines.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun RoutinesScreenPreview() {
    AppTheme {
        RoutinesScreen(navController = rememberNavController())
    }
}
